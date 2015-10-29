package xyz.hearthfire.main;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.hearthfire.datasource.CustomerContextHolder;
import xyz.hearthfire.datasource.CustomerType;
import xyz.hearthfire.entity.TUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * created by fz on 2015/10/27.
 */
public class DynamicDataSourceMain {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EntityManagerFactory entityManagerFactory = ctx.getBean("entityManagerFactory", EntityManagerFactory.class);
        CustomerContextHolder.setCustomerType(CustomerType.PRODUCT);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("select t from TUser t");
        List<TUser> list = query.getResultList();
        for(TUser user : list){
            System.out.println(user.getId() + ", " + user.getUserName() + ", " + user.getCreateTime());
        }
        ctx.close();
    }
}
