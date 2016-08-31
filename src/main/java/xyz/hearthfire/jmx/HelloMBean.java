package xyz.hearthfire.jmx;

/**
 * Created by fz on 2016/8/31.
 */
public interface HelloMBean {
    void setMessage(String message);

    String getMessage();

    void sayHello();

    void sayGoodBye();
}
