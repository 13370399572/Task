package com.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.entity.Task;
import com.redis.service.TaskService;

@RestController
@RequestMapping("task")
public class TaskController {

	@Autowired
	private TaskService taskService;
    
	@PostMapping("save")
	public void save(@RequestBody Task task) {
		try {
			taskService.save(task);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
    
	@PostMapping("update")
	public void update(@RequestBody Task task) {
		try {
			taskService.update(task);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
