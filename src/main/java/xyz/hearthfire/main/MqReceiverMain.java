package xyz.hearthfire.main;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.hearthfire.activemq.MqQueueReceiverService;
import xyz.hearthfire.activemq.MqQueueSenderService;

import javax.jms.JMSException;
import java.util.Map;

/**
 * Created by fz on 2015/11/12.
 */
public class MqReceiverMain {

    private static final String mqQueueDestination = "rhino";

    public static void main(String[] args) throws JMSException {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mq.xml");
        MqQueueReceiverService senderService = ctx.getBean("mqQueueReceiverServiceImpl", MqQueueReceiverService.class);
        Map<String, Object> data = senderService.receiveMessage(mqQueueDestination);
        System.out.println(data);
        ctx.close();
    }
}
