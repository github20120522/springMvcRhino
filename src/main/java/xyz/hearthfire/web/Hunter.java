package xyz.hearthfire.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fz on 2015/10/25.
 */
@Controller
public class Hunter {

    private static final Logger logger = LoggerFactory.getLogger(Hunter.class);

    @RequestMapping("hunter")
    public String index(){
        return "html/hunter";
    }

    public static void main(String[] args) {
        logger.info("Hello World");
    }
}
