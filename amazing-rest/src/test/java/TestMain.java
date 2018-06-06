import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.mapper.api.mybatisMapper.UserMapper;
import com.mapper.vo.User;
import com.rest.ExcelRD;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class TestMain {
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/mybatis001";

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;


    @Before
    public void beforeRun() {
        String resource = "mybatisConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void afterRun() {
        sqlSession.close();
    }

    @Test
    public void runA() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runB() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            List<User> users = userMapper.findUserByName("wangxiaojun");
            new ExcelRD().runB(users);

//            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runB2() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            User user = userMapper.findUserById2(1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runC() {
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("demo", "demo");
        System.out.println(jedis.get("demo"));
        if (null != jedis) {
            jedis.close();
        }
        if (null != jedisPool) {
            jedisPool.close();
        }
    }

    @Test
    public void runD() {
        TestService.TestService1 testMain = new TestService().new TestService1();
        Service service = testMain.startAsync();
        service.awaitRunning();
        try {
            Thread.sleep(1000);
            System.out.println(service.isRunning());
            service.stopAsync();
            service.awaitTerminated();
            Thread.sleep(1000);
            System.out.println(service.isRunning());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runE() {
        TestService.TestService2 testMain2 = new TestService().new TestService2();
        testMain2.addListener(new Service.Listener() {
            @Override
            public void starting() {
                super.starting();
                System.out.println("服务开始启动.....");
            }

            @Override
            public void running() {
                super.running();
                System.out.println("服务开始运行");
            }

            @Override
            public void stopping(Service.State from) {
                super.stopping(from);
                System.out.println("服务关闭中");
            }

            @Override
            public void terminated(Service.State from) {
                super.terminated(from);
                System.out.println("服务终止");
            }

            @Override
            public void failed(Service.State from, Throwable failure) {
                super.failed(from, failure);
                System.out.println("失败，cause：" + failure.getCause());
            }
        }, MoreExecutors.directExecutor());
        Service service2 = testMain2.startAsync();
        service2.awaitRunning();
        try {
            Thread.sleep(1000);
            System.out.println(service2.isRunning() + "   " + service2.state());
            service2.stopAsync();
            service2.awaitTerminated();
            Thread.sleep(1000);
            System.out.println(service2.isRunning());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runF() {
        TestService.TestService3 service = new TestService().new TestService3();
        service.startAsync().awaitRunning();
        System.out.println("服务状态为:" + service.state());

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.stopAsync().awaitTerminated();

        System.out.println("服务状态为:" + service.state());
    }

    @Test
    public void runG() {
        TestService testService = new TestService();
        testService.serviceManager.startAsync();
    }

    @Test
    public void runH() {
        String s = "edec3e6d-59ec-4cf8-ac3c-081e2a54a89fbd849167-77c1-429c-9cdd-619be6680869";
        System.out.println(s.substring(0, 36).length());
        System.out.println(s.substring(0, 36));
        System.out.println(s.substring(36, 72).length());
        System.out.println(s.substring(36, 72));
    }

}
