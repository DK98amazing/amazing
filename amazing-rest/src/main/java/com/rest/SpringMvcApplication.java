package com.rest;

import com.mapper.api.mybatisMapper.UserMapper;
import com.mapper.vo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SpringMvcApplication {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    {
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

    @RequestMapping(value = "/allData", method = RequestMethod.GET)
    public Response getAllData() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = new ArrayList<>();
        try {
            users = userMapper.findUserByName("wangxiaojun");
//            new ExcelRD().runB(users);
//            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(users).build();
    }
}
