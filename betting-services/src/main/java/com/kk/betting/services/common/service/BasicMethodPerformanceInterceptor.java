package com.kk.betting.services.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

public class BasicMethodPerformanceInterceptor extends ExceptionInterceptor {

    protected static final String LOG_SYMBOL = "PERF";
    protected static final Log log = LogFactory.getLog(LOG_SYMBOL);

    @AroundTimeout
    @AroundInvoke
    public Object measureMethodExecutionTime(InvocationContext invocationContext) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
          return invocationContext.proceed();

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
