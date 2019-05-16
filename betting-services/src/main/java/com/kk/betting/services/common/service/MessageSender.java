package com.kk.betting.services.common.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by KK on 2017-05-06.
 */
@Local
public interface MessageSender<T extends Serializable> {
    void sendMessage(T payload);
}
