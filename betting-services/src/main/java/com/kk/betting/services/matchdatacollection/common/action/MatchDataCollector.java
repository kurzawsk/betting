package com.kk.betting.services.matchdatacollection.common.action;

import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import com.kk.betting.services.common.service.TransactionAwarePerformanceInterceptor;
import com.kk.betting.services.matchdatacollection.service.AbstractMatchDataCollector;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-02-27.
 */


@Stateless
@LocalBean
@TransactionManagement(value= TransactionManagementType.BEAN)
public class MatchDataCollector implements com.kk.betting.services.matchdatacollection.service.MatchDataCollector {

    @Inject
    private AbstractMatchDataCollector delegate;

    @Override
    @Schedule(minute = "15,35,55", hour = "*", persistent = false)
    @Interceptors(TransactionAwarePerformanceInterceptor.class)
    public void checkMatchResults() {
        delegate.checkMatchResults();
    }

    @Override
    public void checkMatchResults(LocalDateTime referenceDate) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Schedule(minute = "0", hour = "*", persistent = false)
    @Interceptors(TransactionAwarePerformanceInterceptor.class)
    @TransactionTimeout(value = 20, unit = TimeUnit.MINUTES)
    public void updateMatchOdds() {
        delegate.updateMatchOdds();
    }

    @Override
    @Schedule(minute = "20", hour = "6,18", persistent = false)
    @Interceptors(TransactionAwarePerformanceInterceptor.class)
    public void findAndInsertNewMatches() {
        delegate.findAndInsertNewMatches();
    }
}
