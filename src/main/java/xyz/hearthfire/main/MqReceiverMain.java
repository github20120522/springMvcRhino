package xyz.hearthfire.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.hearthfire.activemq.MqQueueReceiverService;

import javax.jms.JMSException;
import java.util.Map;

/**
 * Created by fz on 2015/11/12.
 */
public class MqReceiverMain {

    private static final String mqQueueDestination = "rhino";

    private static Logger logger = LoggerFactory.getLogger(MqReceiverMain.class);

    public static void main(String[] args) throws JMSException {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mq.xml");
        MqQueueReceiverService senderService = ctx.getBean("mqQueueReceiverServiceImpl", MqQueueReceiverService.class);
        while(true){
            try {
                Map<String, Object> data = senderService.receiveMessage(mqQueueDestination);
                logger.warn(data.toString());
            } catch (Exception e){
                continue;
            }
        }
    }
}
