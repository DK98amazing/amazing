import com.mapper.api.UserMapper;
import com.mapper.vo.User;
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
}
