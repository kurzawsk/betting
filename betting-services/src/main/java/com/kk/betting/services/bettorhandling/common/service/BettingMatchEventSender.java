package com.kk.betting.services.bettorhandling.common.service;

import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.services.common.service.AbstractMessageSender;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 * Created by KK on 2017-05-06.
 */
@Named
public class BettingMatchEventSender extends AbstractMessageSender<MatchBetProposalDTO> {

    private static final String QUEUE_JNDI = "/queue/BettingMatchEventQueue";

    @Resource(mappedName = CONNECTION_FACTORY_JNDI)
    private ConnectionFactory factory;

    @Resource(mappedName = JMS_BASE_JNDI + QUEUE_JNDI)
    private Queue target;

    @Override
    public void sendMessage(MatchBetProposalDTO payload) {
        send(factory, target, payload);
    }


}

