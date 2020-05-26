package com.redis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redis.entity.User;
import com.redis.service.userService;

@Controller
public class UserController {
	
	@Autowired
	private userService userService;

	@ResponseBody
    @RequestMapping("/findUserById")
    public Map<String, Object> findUserById(@RequestParam int id){
        User user = userService.findUserById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("uid", user.getUid());
        result.put("uname", user.getUserName());
        result.put("pass", user.getPassWord());
        result.put("salary", user.getSalary());
        return result;
    }
	
	
}
