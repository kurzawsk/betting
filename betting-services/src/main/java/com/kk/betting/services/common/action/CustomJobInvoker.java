package com.kk.betting.services.common.action;

import com.kk.betting.domain.CustomJob;
import com.kk.betting.services.common.dao.CustomJobDao;
import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import com.kk.betting.services.common.service.CustomJobLogic;
import com.kk.betting.services.common.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-09-02.
 */

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CustomJobInvoker {

    private static final Log log = LogFactory.getLog(CustomJobInvoker.class);

    @Inject
    private CustomJobDao customJobDao;

    @Resource
    private TimerService timerService;


    @PostConstruct
    public void initTimers() {
        customJobDao.getActiveCustomJobs().stream().forEach(cj ->
                timerService.createCalendarTimer(DateUtil.getScheduleExpression(cj.getScheduleExpression()), new TimerConfig(cj.getId(), false)));
    }

    @Timeout
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    @TransactionTimeout(value = 30, unit = TimeUnit.MINUTES)
    @Lock(LockType.READ)
    public void execute(Timer timer) {
        try {
            long customJobId = (long) timer.getInfo();
            log.info("About to invoke custom timer logic for: " + customJobId);

            CustomJob customJob = customJobDao.findById(customJobId);
            CustomJobLogic customJobLogic = (CustomJobLogic) CDI.current().select(Class.forName(customJob.getLogicImpClass())).get();
            customJobLogic.init(customJob.getLogicParameters());
            customJobLogic.run();

            log.info("Custom timer logic for: " + customJobId + " finished");
        } catch (Exception e) {
            throw new RuntimeException("Exception ocurred during invoking custom job", e);
        }
    }
}
