package com.kk.betting.services.report.action;

import com.kk.betting.services.common.action.EmailSender;
import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import com.kk.betting.services.report.service.BettorReportGenerator;
import com.kk.betting.services.report.service.MatchDataReportGenerator;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by KK on 2017-06-10.
 */
@Stateless
@Local
public class ReportCreationInvoker {

    private static final String DEFAULT_EMAIL_CONTENT_TYPE = "text/html";

    @Inject
    private EmailSender emailSender;

    @Inject
    private BettorReportGenerator bettorReportGenerator;


    @Inject
    private MatchDataReportGenerator matchDataReportGenerator;

    @Schedule(minute = "0", hour = "8", persistent = false)
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    public void prepareMatchReport() {
        LocalDate referenceDate = LocalDate.now().minusDays(1);
        String content = matchDataReportGenerator.createDailyMatchReport(LocalDate.now().minusDays(1));
        emailSender.sendEmail("Daily match statistics report for: " + referenceDate, content, DEFAULT_EMAIL_CONTENT_TYPE);
    }

    @Schedule(minute = "0", hour = "9", persistent = false)
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    public void prepareAllActiveBettorsHistoryReport() {
        String content = bettorReportGenerator.generateBettingEventDiscrepanciesReport() +
                bettorReportGenerator.generateAllActiveBettorsHistoryReport();
        emailSender.sendEmail("Betting event history report", content, DEFAULT_EMAIL_CONTENT_TYPE);
    }

    public void prepareMatchReport(LocalDateTime from, LocalDateTime to) {
        String content = matchDataReportGenerator.createPeriodMatchReport(from, to);
        emailSender.sendEmail("Daily match statistics report for: " + from + " - " + to, content, DEFAULT_EMAIL_CONTENT_TYPE);
    }
}
