package com.kk.betting.services.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

/**
 * Created by KK on 2017-10-05.
 */
public class TransactionAwarePerformanceInterceptor extends BasicMethodPerformanceInterceptor {

    protected static final String LOG_SYMBOL = "PERF";
    protected static final Log log = LogFactory.getLog(LOG_SYMBOL);

    @Resource
    UserTransaction tx;

    @AroundTimeout
    @AroundInvoke
    public Object measureMethodExecutionTime(InvocationContext invocationContext) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            tx.begin();
            Object result = invocationContext.proceed();
            tx.commit();
            return result;
        } catch (Exception e) {
            handleException(invocationContext, e);
            return null;
        } finally {
            long endTime = System.currentTimeMillis() - startTime;
            String className = invocationContext.getMethod().getDeclaringClass().getSimpleName();
            log.info("Method " + className + "." + invocationContext.getMethod().getName() + " took " + endTime
                    + " [ms]");

        }
    }
}
