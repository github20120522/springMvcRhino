package xyz.hearthfire.web;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hearthfire.activemq.MqQueueReceiverService;
import xyz.hearthfire.activemq.MqQueueSenderService;
import xyz.hearthfire.cache.CacheServiceImpl;

import javax.jms.JMSException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fz on 2015/10/25.
 */
@Controller
public class HunterController {

    private static final Logger logger = LoggerFactory.getLogger(HunterController.class);

    @Autowired
    private CacheServiceImpl cacheServiceImpl;

    @Autowired
    private MqQueueSenderService mqQueueSenderService;

    @Autowired
    private MqQueueReceiverService mqQueueReceiverService;

    private static final String mqQueueDestination = "rhino";

    @RequestMapping("/hunter")
    public String index(){
        return "html/hunter";
    }

    @RequestMapping("/putCache/{key}/{value}")
    @ResponseBody
    public String putCache(@PathVariable String key, @PathVariable String value){
        cacheServiceImpl.putCache(key, value);
        return "success";
    }

    @RequestMapping("/putCache/{key}/{value}/{time}")
    @ResponseBody
    public String putCache(@PathVariable String key, @PathVariable String value, @PathVariable long time){
        cacheServiceImpl.putCache(key, value, time);
        return "success";
    }

    @RequestMapping("/replaceCache/{key}/{value}")
    @ResponseBody
    public String replaceCache(@PathVariable String key, @PathVariable String value){
        cacheServiceImpl.replaceCache(key, value);
        return "success";
    }

    @RequestMapping("/replaceCache/{key}/{value}/{time}")
    @ResponseBody
    public String replaceCache(@PathVariable String key, @PathVariable String value, @PathVariable long time){
        cacheServiceImpl.replaceCache(key, value, time);
        return "success";
    }

    @RequestMapping("/getCache/{key}")
    @ResponseBody
    public String getCache(@PathVariable String key){
        Object obj = cacheServiceImpl.getCache(key);
        String result = "null";
        if(obj != null){
            result = obj.toString();
        }
        return result;
    }

    @RequestMapping("/removeCache/{key}")
    @ResponseBody
    public boolean removeCache(@PathVariable String key){
        boolean b = false;
        cacheServiceImpl.removeCache(key);
        if(cacheServiceImpl.getCache(key) == null){
            b = true;
        }
        return b;
    }

    @RequestMapping("/sendHunterMessage/{message}")
    @ResponseBody
    public String sendHunterMessage(@PathVariable String message){
        HashMap<String, Object> data = Maps.newHashMap();
        data.put("hunter", message);
        String result;
        try {
            mqQueueSenderService.sendMessage(mqQueueDestination, data);
            result = "success";
        } catch (Exception e) {
            result = "failure";
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/receiveHunterMessage")
    @ResponseBody
    public Map<String, Object> receiveHunterMessage() throws JMSException {
        Map<String, Object> result = Maps.newHashMap();
        try {
            Map<String, Object> data = mqQueueReceiverService.receiveMessage(mqQueueDestination);
            result.putAll(data);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("errorMessage", e.toString());
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        logger.info("Hello World");
    }
}
