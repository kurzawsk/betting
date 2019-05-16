package com.kk.betting.services.bettorhandling.common.action;

import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.dto.AlignBettorResourcesEventDTO;
import com.kk.betting.services.bettorhandling.common.service.BettingMatchEventProcessor;
import com.kk.betting.services.common.service.ExceptionInterceptor;
import com.kk.betting.services.matchdatacollection.service.AbstractMatchDataCollector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-02-05.
 */


@MessageDriven(name = "BettingMatchEventConsumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/BettingMatchEventQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")})
public class BettingMatchEventConsumer implements MessageListener {

    @Inject
    private BettingMatchEventProcessor bettingMatchEventProcessor;

    @Override
    @Interceptors(ExceptionInterceptor.class)
    @TransactionTimeout(value = 120, unit = TimeUnit.MINUTES)
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Object payload = ((ObjectMessage) message).getObject();
                if (payload instanceof MatchFinishEventDTO[]) {
                    bettingMatchEventProcessor.processMatchFinishEvent((MatchFinishEventDTO[]) payload);
                } else if (payload instanceof MatchBetProposalDTO) {
                    bettingMatchEventProcessor.processMatchBetProposalEvent((MatchBetProposalDTO) payload);
                }else if (payload instanceof AlignBettorResourcesEventDTO) {
                    bettingMatchEventProcessor.processAlignBettorResourcesEvent((AlignBettorResourcesEventDTO) payload);
                }
                else {
                    throw new IllegalArgumentException("Unsupported message object class: " + payload.getClass());
                }
            } catch (JMSException e) {
                throw new RuntimeException("Exception occurred while processing JMS message.", e);
            }
        }

    }


}
