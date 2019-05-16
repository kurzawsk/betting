package com.kk.betting.services.bettorhandling.common.action;

import com.kk.betting.services.bettorhandling.common.service.BetOddSourceInvoker;
import com.kk.betting.services.bettorhandling.common.service.RawBettingProposalSender;
import com.kk.betting.services.common.util.DateUtil;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.domain.BettingProposalSource;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-05-20.
 */


@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TimedBetOddSourceInvoker {

    private static final Log log = LogFactory.getLog(TimedBetOddSourceInvoker.class);

    @Inject
    private RawBettingProposalSender messageSender;

    @Inject
    private WebPageBrowser webPageBrowser;

    @Inject
    private BetOddSourceInvoker betOddSourceInvoker;

    @Resource
    private TimerService timerService;

    @PostConstruct
    @Lock(LockType.WRITE)
    public void initTimers() {
        betOddSourceInvoker.getBettingLogicBinding().keySet().stream().forEach(bs ->
                timerService.createCalendarTimer(DateUtil.getScheduleExpression(bs.getScheduleExpression()), new TimerConfig(bs, false)));
    }

    @Timeout
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    @TransactionTimeout(value = 60, unit = TimeUnit.MINUTES)
    @Lock(LockType.READ)
    public void execute(Timer timer) {
        log.info("About to invoke betting logic for: " + ((BettingProposalSource)timer.getInfo()).getLogicImpClass());
        List<MatchBetProposalRawDTO> proposals = betOddSourceInvoker.invokeBetOddSourceLogic((BettingProposalSource) timer.getInfo());
        log.info("Got : " + proposals.size() + " new match betting proposals for: " + proposals.stream().map(MatchBetProposalRawDTO::getMatchId).distinct().count() + " matches");
        proposals.stream().forEach(proposal -> messageSender.sendMessage(proposal));
    }
}
