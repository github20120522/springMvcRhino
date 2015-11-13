package xyz.hearthfire.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Created by fz on 2015/11/12.
 */
@Service
public class MqQueueSenderServiceImpl implements MqQueueSenderService {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(String queue, Serializable object) {
        jmsTemplate.send(queue, session -> {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(object);
            return objectMessage;
        });
    }
}
