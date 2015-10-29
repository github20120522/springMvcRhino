package xyz.hearthfire.cache;

import java.io.Serializable;

/**
 * Created by fz on 2015/10/29.
 */
public class TimerCacheBean implements Serializable{

    private Object target;

    private long timer;

    public TimerCacheBean(Object target, long timer) {
        this.target = target;
        this.timer = timer;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }
}
