package xyz.hearthfire.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fz on 2015/12/8.
 */
@Component
public class HunterTask {

    /*
    private static boolean lock = false;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() throws InterruptedException {
        if(!lock){
            lock = true;
            System.out.println("The time is now " + dateFormat.format(new Date()));
            Thread.sleep(6000);
            lock = false;
        }else{
            System.out.println("Never print...");
        }
    }
    */

}
