package com.kk.betting.services.matchdatacollection.service;

import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.services.common.service.AbstractMessageSender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 * Created by KK on 2017-06-08.
 */
@Stateless
public class MatchFinishedEventSender extends AbstractMessageSender<MatchFinishEventDTO[]> {

    private static final String QUEUE_JNDI = "/queue/BettingMatchEventQueue";

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName = JMS_BASE_JNDI +  QUEUE_JNDI)
    private Queue target;

    @Override
    public void sendMessage(MatchFinishEventDTO[] payload) {
        send(factory, target, payload);
    }

}