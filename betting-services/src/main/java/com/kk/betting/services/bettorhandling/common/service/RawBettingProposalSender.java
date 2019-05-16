package com.kk.betting.services.bettorhandling.common.service;

import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.service.AbstractMessageSender;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 * Created by KK on 2017-06-08.
 */
@Named
public class RawBettingProposalSender extends AbstractMessageSender<MatchBetProposalRawDTO> {

    private static final String QUEUE_JNDI = "/queue/BettingProposalQueue";

    @Resource(mappedName = CONNECTION_FACTORY_JNDI)
    private ConnectionFactory factory;

    @Resource(mappedName = JMS_BASE_JNDI + QUEUE_JNDI)
    private Queue target;

    @Override
    public void sendMessage(MatchBetProposalRawDTO payload) {
        send(factory, target, payload);
    }

}