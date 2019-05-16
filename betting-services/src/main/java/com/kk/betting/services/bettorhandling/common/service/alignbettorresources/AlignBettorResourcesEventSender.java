package com.kk.betting.services.bettorhandling.common.service.alignbettorresources;

import com.kk.betting.dto.AlignBettorResourcesEventDTO;
import com.kk.betting.services.common.service.AbstractMessageSender;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 * Created by KK on 2017-09-11.
 */
public class AlignBettorResourcesEventSender extends AbstractMessageSender<AlignBettorResourcesEventDTO> {

    private static final String QUEUE_JNDI = "/queue/BettingMatchEventQueue";

    @Resource(mappedName = CONNECTION_FACTORY_JNDI)
    private ConnectionFactory factory;

    @Resource(mappedName = JMS_BASE_JNDI + QUEUE_JNDI)
    private Queue target;

    @Override
    public void sendMessage(AlignBettorResourcesEventDTO payload) {
        send(factory, target, payload);
    }


}
