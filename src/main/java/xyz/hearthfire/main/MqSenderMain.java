package xyz.hearthfire.main;

import com.google.common.collect.Maps;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.hearthfire.activemq.MqQueueSenderService;

import java.util.HashMap;

/**
 * Created by fz on 2015/11/12.
 */
public class MqSenderMain {

    private static final String mqQueueDestination = "rhino";

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mq.xml");
        MqQueueSenderService senderService = ctx.getBean("mqQueueSenderServiceImpl", MqQueueSenderService.class);
        HashMap<String, Object> data = Maps.newHashMap();
        data.put("hello", "world");
        data.put("1", 2);
        senderService.sendMessage(mqQueueDestination, data);
        ctx.close();
    }
}
