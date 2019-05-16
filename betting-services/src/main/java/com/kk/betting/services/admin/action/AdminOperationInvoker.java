package com.kk.betting.services.admin.action;

import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.BetOddSourceInvoker;
import com.kk.betting.services.bettorhandling.common.service.RawBettingProposalSender;
import com.kk.betting.services.bettorhandling.typersi.service.TypersiHistoryService;
import com.kk.betting.services.common.dao.BettingProposalSourceDao;
import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import com.kk.betting.services.matchdatacollection.common.action.MatchDataCollector;
import com.kk.betting.services.report.action.ReportCreationInvoker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-06-29.
 */
@Stateless
//TODO leave only pure admin operations in here (report, etc should be moved to seperate modules)
public class AdminOperationInvoker implements AdminOperationInvokerLocal, AdminOperationInvokerRemote {

    private static final Log log = LogFactory.getLog(AdminOperationInvoker.class);

    @Inject
    private MatchDataCollector matchDataCollector;

    @Inject
    private RawBettingProposalSender messageSender;

    @Inject
    private RawBettingProposalSender rawBettingProposalSender;

    @Inject
    private ReportCreationInvoker reportCreationInvoker;

    @Inject
    private BetOddSourceInvoker betOddSourceInvoker;

    @Inject
    private BettingProposalSourceDao bettingProposalSourceDao;

    @Inject
    private TypersiHistoryService typersiHistoryService;

    @Override
    public void checkMatchResults() {
        matchDataCollector.checkMatchResults();
    }

    @Override
    public void updateMatchOdds() {
        matchDataCollector.updateMatchOdds();
    }

    @Override
    public void findAndInsertNewMatches() {
        matchDataCollector.findAndInsertNewMatches();
    }

    @Override
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    public void sendBettingProposal(Long matchId, String expectedMatchResult, BigDecimal odd, String sourceName) {
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().
                setMatchId(matchId).setExpectedMatchResult(expectedMatchResult).setOdd(odd).setProposalSource(sourceName).build();
        rawBettingProposalSender.sendMessage(matchBetProposalRawDTO);
    }

    @Override
    public void prepareMatchReport(LocalDateTime from, LocalDateTime to) {
        reportCreationInvoker.prepareMatchReport(from, to);
    }

    @Override
    public void prepareAllActiveBettorsHistoryReport() {
        reportCreationInvoker.prepareAllActiveBettorsHistoryReport();
    }

    @Override
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    @TransactionTimeout(value = 60, unit = TimeUnit.MINUTES)
    public void invokeBetOddSource(Long bettingProposalSourceId) {
        List<MatchBetProposalRawDTO> proposals = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSourceDao.findById(bettingProposalSourceId));
        log.info("Got : " + proposals.size() + " new match betting proposals for: " + proposals.stream().map(MatchBetProposalRawDTO::getMatchId).distinct().count() + " matches");
        proposals.stream().forEach(proposal -> messageSender.sendMessage(proposal));
    }


}
