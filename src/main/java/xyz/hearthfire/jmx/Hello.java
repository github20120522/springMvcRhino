package xyz.hearthfire.jmx;

/**
 * Created by fz on 2016/8/31.
 */
public class Hello implements HelloMBean {
    private String message = null;

    public Hello() {
        message = "Hello, world";
    }

    public Hello(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void sayHello() {
        System.out.println(message);
    }

    public void sayGoodBye() {
        System.out.println("Good bye: " + message);
    }
}
