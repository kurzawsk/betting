package com.kk.betting.services.bettorhandling.common.action;

import com.kk.betting.services.bettorhandling.common.service.BettingMatchEventSender;
import com.kk.betting.services.bettorhandling.common.service.BettingProposalProcessor;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.service.BasicMethodPerformanceInterceptor;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-05-06.
 */


@MessageDriven(name = "BettingProposalConsumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/BettingProposalQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1"),
})
public class BettingProposalConsumer implements MessageListener {

    @Inject
    private BettingMatchEventSender messageSender;

    @Inject
    private BettingProposalProcessor bettingProposalProcessor;

    @Override
    @Interceptors(BasicMethodPerformanceInterceptor.class)
    @TransactionTimeout(value = 60, unit = TimeUnit.MINUTES)
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Object payload = ((ObjectMessage) message).getObject();
                if (payload instanceof MatchBetProposalRawDTO) {
                    List<MatchBetProposalDTO> bettorsProposals = bettingProposalProcessor.processBettingProposal((MatchBetProposalRawDTO) payload);
                    bettorsProposals.stream().forEach(bettorsProposal -> messageSender.sendMessage(bettorsProposal));
                } else {
                    throw new JMSException("Unsupported message object: " + payload);
                }
            } catch (JMSException e) {
                throw new RuntimeException("Exception occurred while processing JMS message.", e);
            }
        }
    }

}
