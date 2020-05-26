package com.redis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.redis.entity.User;

@Mapper
public interface UserDao {
	
	List<User> queryAll();

    User findUserById(int id);

    int updateUser(@Param("user") User user);

    int deleteUserById(int id);

}
