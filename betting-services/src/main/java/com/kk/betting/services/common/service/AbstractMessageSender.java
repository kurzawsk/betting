package com.kk.betting.services.common.service;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by KK on 2017-05-07.
 */
public abstract class AbstractMessageSender<T extends Serializable> implements MessageSender<T> {

    protected static final String CONNECTION_FACTORY_JNDI = "java:/ConnectionFactory";
    protected static final String JMS_BASE_JNDI = "java:jboss/exported/jms";

    protected final void send(ConnectionFactory factory, Queue target, T payload) {
        try (Connection connection = factory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(target);
            producer.send(session.createObjectMessage(payload),  DeliveryMode.NON_PERSISTENT, 0 ,120000);

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}


