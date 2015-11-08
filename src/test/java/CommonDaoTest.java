import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.hearthfire.dao.CommonDao;
import xyz.hearthfire.dao.PagerData;
import xyz.hearthfire.entity.TUser;
import xyz.hearthfire.utils.Constants;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by fz on 2015/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommonDaoTest {

    @Autowired
    CommonDao commonDao;

    @Test
    public void userQueryListTest(){
        String hql = "from TUser";
        List<TUser> list = commonDao.queryList(hql);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void userQueryListTest2(){
        String hql = "from TUser where userName = :userName";
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "rhino");
        List<TUser> list = commonDao.queryList(hql, params);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void userQueryListLimitCountTest(){
        String hql = "from TUser";
        List<TUser> list = commonDao.queryListLimitCount(hql, 1);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void userQueryListLimitCountTest2(){
        String hql = "from TUser where userName like :userName";
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "%rhino%");
        List<TUser> list = commonDao.queryListLimitCount(hql, params, 1);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void userQueryPageListTest(){
        String hql = "from TUser";
        List<TUser> list = commonDao.queryPageList(hql, 1, 1);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void userQueryPageListTest2(){
        String hql = "from TUser where userName like :userName";
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "%rhino%");
        List<TUser> list = commonDao.queryPageList(hql, params, 1, 1);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void userQueryPagerDataTest(){
        String countHql = "select count(*) from TUser";
        String hql = "from TUser";
        PagerData<TUser> pagerData = commonDao.queryPagerData(countHql, hql, 1, 1);
        Assert.assertEquals(1, pagerData.getData().size());
        Assert.assertEquals(2, pagerData.getTotalCount());
    }

    @Test
    public void userQueryPagerDataTest2(){
        String countHql = "select count(*) from TUser where userName like :userName";
        String hql = "from TUser where userName like :userName";
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "%rhino%");
        PagerData<TUser> pagerData = commonDao.queryPagerData(countHql, hql, params, 2, 1);
        Assert.assertEquals(1, pagerData.getData().size());
        Assert.assertEquals(2, pagerData.getTotalCount());
    }

    @Test
         public void userGetSingleTest(){
        String hql = "from TUser where id = 1";
        TUser user = commonDao.getSingle(hql);
        Assert.assertEquals("rhino", user.getUserName());
        Assert.assertEquals("犀牛", user.getRealName());
    }

    @Test
    public void userGetSingleTest2(){
        String hql = "from TUser where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 2);
        TUser user = commonDao.getSingle(hql, params);
        Assert.assertEquals("rhino2", user.getUserName());
        Assert.assertEquals("犀牛2", user.getRealName());
    }

    @Test
    public void userFindByIdTest(){
        TUser user = commonDao.findById(TUser.class, 2);
        Assert.assertEquals("rhino2", user.getUserName());
        Assert.assertEquals("犀牛2", user.getRealName());
    }

    @Test
    public void userQueryListBySqlTest(){
        String sql = "select * from t_user";
        List<TUser> list = commonDao.queryListBySql(sql, TUser.class);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void userQueryListBySqlTest2(){
        String sql = "select * from t_user";
        String sql2 = "select id, user_name as userName, password, real_name as realName from t_user";
        List<Map> list = commonDao.queryListBySql(sql, Map.class);
        List<Map> list2 = commonDao.queryListBySql(sql2, Map.class);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list2.size());
    }

    @Test
    public void userQueryListBySqlTest3(){
        String sql = "select * from t_user where id = :id";
        String sql2 = "select id, user_name as userName, password, real_name as realName from t_user where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 2);
        List<Map> list = commonDao.queryListBySql(sql, params, Map.class);
        List<Map> list2 = commonDao.queryListBySql(sql2, params, Map.class);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list2.size());
    }

    @Test
    public void userQueryPagerDataBySqlTest(){
        String countSql = "select count(*) from t_user";
        String sql = "select * from t_user";
        PagerData<Map> pagerData = commonDao.queryPagerDataBySql(countSql, sql, Map.class);
        PagerData<TUser> pagerData2 = commonDao.queryPagerDataBySql(countSql, sql, TUser.class);
        Assert.assertEquals(2, pagerData.getData().size());
        Assert.assertEquals(2, pagerData.getTotalCount());
        Assert.assertEquals(2, pagerData2.getData().size());
        Assert.assertEquals(2, pagerData2.getTotalCount());
    }

    @Test
    public void userQueryPagerDataBySqlTest2(){
        String countSql = "select count(*) from t_user where id = :id";
        String sql = "select * from t_user where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 1);
        PagerData<Map> pagerData = commonDao.queryPagerDataBySql(countSql, sql, params, Map.class);
        PagerData<TUser> pagerData2 = commonDao.queryPagerDataBySql(countSql, sql, params, TUser.class);
        Assert.assertEquals(1, pagerData.getData().size());
        Assert.assertEquals(1, pagerData.getTotalCount());
        Assert.assertEquals(1, pagerData2.getData().size());
        Assert.assertEquals(1, pagerData2.getTotalCount());
    }

    @Test
    public void userGetSingleBySqlTest(){
        String sql = "select * from t_user where id = 1";
        Map map = commonDao.getSingleBySql(sql, Map.class);
        TUser user = commonDao.getSingleBySql(sql, TUser.class);
        Assert.assertEquals("2015-11-07 16:45:48.0", map.get("create_time").toString());
        Assert.assertEquals("2015-11-07 16:45:48.0", user.getCreateTime().toString());
    }

    @Test
    public void userGetSingleBySqlTest2(){
        String sql = "select * from t_user where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 1);
        Map map = commonDao.getSingleBySql(sql, params, Map.class);
        TUser user = commonDao.getSingleBySql(sql, params, TUser.class);
        Assert.assertEquals("2015-11-07 16:45:48.0", map.get("create_time").toString());
        Assert.assertEquals("2015-11-07 16:45:48.0", user.getCreateTime().toString());
    }

    @Test
    public void userSaveTest(){
        TUser user = new TUser();
        user.setUserName("rhino4");
        user.setPassword("222");
        user.setRealName("犀牛4");
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonDao.save(user);
        Assert.assertNotEquals(0, user.getId());
    }

    @Test
    public void userUpdateTest(){
        TUser user = commonDao.findById(TUser.class, 4);
        user.setUserName("rhino5");
        user.setPassword("222");
        user.setRealName("犀牛5");
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonDao.update(user);
        Assert.assertNotEquals(0, user.getId());
    }

    @Test
    public void userRemoveTest(){
        TUser user = commonDao.findById(TUser.class, 5);
        commonDao.delete(user);
        TUser user2 = commonDao.findById(TUser.class, 5);
        Assert.assertNull(user2);
    }

    @Test
    public void userExecuteTest(){
        String hql = "delete TUser where id = 8";
        int c = commonDao.execute(hql);
        Assert.assertEquals(1, c);
    }

    @Test
    public void userExecuteTest2(){
        String hql = "update TUser set userName = :userName where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 6);
        params.put("userName", "rhino7");
        commonDao.execute(hql, params);
        String hql2 = "from TUser where id = 6";
        TUser user = commonDao.getSingle(hql2);
        Assert.assertEquals("rhino7", user.getUserName());
    }

    @Test
    public void userExecuteBySqlTest(){
        String sql = "insert into t_user(user_name, password, real_name, create_time, is_activity) values('rhino3', 222, '犀牛3', sysdate(), 1)";
        int count = commonDao.executeBySql(sql);
        Assert.assertEquals(1, count);
    }

    @Test
    public void userExecuteBySqlTest2(){
        String sql = "insert into t_user(user_name, password, real_name, create_time, is_activity) values(:userName, :password, :realName, :createTime, :isActivity)";
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "rhino6");
        params.put("password", 222);
        params.put("realName", "犀牛6");
        params.put("createTime", new Timestamp(System.currentTimeMillis()));
        params.put("isActivity", Constants.IS_ACTIVITY_FALSE);
        int count = commonDao.executeBySql(sql, params);
        Assert.assertEquals(1, count);
    }

}
