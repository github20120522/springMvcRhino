package xyz.hearthfire.activemq;

import javax.jms.JMSException;

/**
 * Created by fz on 2015/11/12.
 */
public interface MqQueueReceiverService {

    <T> T receiveMessage(String queueDestination) throws JMSException;
}
