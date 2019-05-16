package com.kk.betting.services.common.service;

import com.kk.betting.services.common.action.EmailSender;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

/**
 * Created by KK on 2017-09-05.
 */
public class ExceptionInterceptor {

    private static final String LOG_SYMBOL = "ERROR_INC";
    private static final Log log = LogFactory.getLog(LOG_SYMBOL);


    @Inject
    private EmailSender emailSender;

    @AroundTimeout
    @AroundInvoke
    public Object measureMethodExecutionTime(InvocationContext invocationContext) throws Exception {
        try {
            return invocationContext.proceed();
        } catch (Exception e) {
            handleException(invocationContext, e);
            return null;
        }
    }

    void handleException(InvocationContext invocationContext, Exception e) {
        StringBuilder messageBuilder = new StringBuilder("Exception occurred while calling: ");
        messageBuilder.append(invocationContext.getMethod().getDeclaringClass().getSimpleName()).append(",");
        messageBuilder.append(invocationContext.getMethod().getName());
        log.error(messageBuilder.toString(), e);
        emailSender.sendEmail(messageBuilder.toString(), "Exception message:\n" + e.getMessage() + " Stack: " + ExceptionUtils.getStackTrace(e));
    }
}
