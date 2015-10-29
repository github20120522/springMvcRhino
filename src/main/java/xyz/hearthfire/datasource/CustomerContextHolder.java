package xyz.hearthfire.datasource;

import org.springframework.util.Assert;

/**
 * Created by fz on 2015/10/27.
 */
public class CustomerContextHolder {

    private static final ThreadLocal<CustomerType> contextHolder = new ThreadLocal<CustomerType>();

    public static void setCustomerType(CustomerType customerType) {
        Assert.notNull(customerType, "customerType cannot be null");
        contextHolder.set(customerType);
    }

    public static CustomerType getCustomerType() {
        return (CustomerType) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}
