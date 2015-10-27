package xyz.hearthfire.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hearthfire.utils.CacheUtil;

/**
 * Created by fz on 2015/10/25.
 */
@Controller
public class HunterController {

    private static final Logger logger = LoggerFactory.getLogger(HunterController.class);

    @Autowired
    private CacheUtil cacheUtil;

    @RequestMapping("/hunter")
    public String index(){
        return "html/hunter";
    }

    @RequestMapping("/putCache/{key}/{value}")
    @ResponseBody
    public String putCache(@PathVariable String key, @PathVariable String value){
        cacheUtil.putCache(key, value);
        return "success";
    }

    @RequestMapping("/replaceCache/{key}/{value}")
    @ResponseBody
    public String replaceCache(@PathVariable String key, @PathVariable String value){
        cacheUtil.replaceCache(key, value);
        return "success";
    }

    @RequestMapping("/getCache/{key}")
    @ResponseBody
    public String getCache(@PathVariable String key){
        Object obj = cacheUtil.getCache(key);
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
        cacheUtil.removeCache(key);
        if(cacheUtil.getCache(key) == null){
            b = true;
        }
        return b;
    }

    public static void main(String[] args) {
        logger.info("Hello World");
    }
}
