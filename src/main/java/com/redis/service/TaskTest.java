package com.redis.service;

import org.springframework.stereotype.Component;

@Component("taskTest")
public class TaskTest {
	
	public void test() {
		System.out.println("展示"+"无参函数");
	}
    
	public void test(String param1 ,Integer param2) {
		System.out.println("参数展示"+param1+param2);
	}

}
