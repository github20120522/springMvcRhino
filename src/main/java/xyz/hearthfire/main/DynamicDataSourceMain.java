package xyz.hearthfire.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceMain.class);

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EntityManagerFactory entityManagerFactory = ctx.getBean("entityManagerFactory", EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("select t from TUser t");
        List<TUser> list = query.getResultList();
        list.forEach(DynamicDataSourceMain::logUser);
        CustomerContextHolder.setCustomerType(CustomerType.PRODUCT);
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Query query2 = entityManager2.createQuery("select t from TUser t");
        List<TUser> list2 = query2.getResultList();
        list2.forEach(DynamicDataSourceMain::logUser);
        ctx.close();
    }

    private static void logUser(TUser user){
        logger.debug("userId：" + user.getId());
        logger.debug("userName：" + user.getUserName());
        logger.debug("userPassword：" + user.getPassword());
        logger.debug("realName：" + user.getRealName());
        logger.debug("createTime：" + user.getCreateTime());
        logger.debug("isActivity：" + user.getIsActivity());
        logger.debug("=========================================");
    }
}
