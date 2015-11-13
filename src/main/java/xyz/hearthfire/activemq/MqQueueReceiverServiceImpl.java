package xyz.hearthfire.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Created by fz on 2015/11/12.
 */
@Service
public class MqQueueReceiverServiceImpl implements MqQueueReceiverService {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Override
    public <T> T receiveMessage(String queueDestination) throws JMSException {
        return (T)(((ObjectMessage)jmsTemplate.receive(queueDestination)).getObject());
    }
}
