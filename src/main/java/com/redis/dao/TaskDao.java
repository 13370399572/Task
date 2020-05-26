package com.redis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.redis.entity.Task;

@Mapper
public interface TaskDao {
	
	int save(@Param("param") Task task);
	
	int update(@Param("param") Task task);
	

}
