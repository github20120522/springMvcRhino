package xyz.hearthfire.activemq;

import java.io.Serializable;

/**
 * Created by fz on 2015/11/12.
 */
public interface MqQueueSenderService {

    void sendMessage(String queue, Serializable object);
}
