package com.mapper.api.mybatisMapper;

import com.mapper.vo.User;

import java.util.List;

public interface UserMapper {
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //根据用户名列查询用户列表
    public List<User> findUserByName(String name) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public boolean deleteUser(int id) throws Exception;

    //更新用户
    public boolean updateUser(User user) throws Exception;

    //根据id查询用户信息，使用resultMap输出
    public User findUserByIdResultMap(int id) throws Exception;
}
