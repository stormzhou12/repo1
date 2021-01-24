package com.lagou.test;

import com.lagou.domain.Orders;
import com.lagou.domain.User;
import com.lagou.mapper.OrderMapper;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MybatisTest {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();



    }
    @After
    public void after(){
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    /*
    测试查询方法
     */
    public void testSelect() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }

    }
    /*
    测试添加方法
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("娜美");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("上海徐汇");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.save(user);
    }
    /*
    测试修改方法
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(5);
        user.setUsername("柳岩");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("上海静安");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.update(user);
    }
    /*
    测试删除方法
     */
    @Test
    public void testDelete(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.delete(5);
    }
    /*
    根据ID查询用户
     */
    @Test
    public void testfindById(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findById(1);
        System.out.println(user);
    }



    /*
    一对一查询(注解方式)
     */
    @Test
    public void test01(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = mapper.findAllWithUser();
        for (Orders order : orders) {
            System.out.println(order);
            System.out.println(order.getUser());
        }
    }
    /*
    根据uid查询订单
     */
    @Test
    public void testfindOrders(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = mapper.findOrderByUid(1);
        for (Orders order : orders) {
            System.out.println(order);
        }
    }
    /*
    一对多查询(注解方式)
     */
    @Test
    public void test02(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAllWithOrders();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
