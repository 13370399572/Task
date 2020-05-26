package com.redis.service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.common.CronTaskRegistrar;
import com.redis.dao.TaskDao;
import com.redis.entity.Task;

@Service
public class TaskService {
	private final static Map<String, SchedulingRunnable> concurrentHashMap = new ConcurrentHashMap<>();
	//private ConcurrentHashMap<String, SchedulingRunnable> concurrentHashMap;
	@Autowired
	CronTaskRegistrar cronTaskRegistrar;

	@Autowired
	private TaskDao taskDao;
	/**
	 * 添加任务
	 * @param task
	 * @throws InterruptedException
	 */
	public void save(Task task) throws InterruptedException {
		if (task.getState().equals("1")) {
			SchedulingRunnable task1 = new SchedulingRunnable(task.getKind(), task.getMethod(), task.getParams());
			cronTaskRegistrar.addCronTask(task1, task.getCron());
			
			// 便于观察
			System.out.println(task.getName());
			String ddString = task.getName();
			if (Objects.nonNull(task1)) {
				concurrentHashMap.put(ddString, task1);
			}
			
			taskDao.save(task);
			Thread.sleep(3000000);
		}else {
			taskDao.save(task);
		}
	}
    
	/**
	 * 修改任务
	 * @param task
	 * @throws InterruptedException
	 */
	public void update(Task task) throws InterruptedException {
		SchedulingRunnable task22 =  concurrentHashMap.get(task.getName());
		if (Objects.nonNull(task22)) {
            if (task.getState().equals("0")) {
            cronTaskRegistrar.removeCronTask(task22);	
			}else {
		    cronTaskRegistrar.removeCronTask(task22);	
		    SchedulingRunnable task1 = new SchedulingRunnable(task.getKind(), task.getMethod(), task.getParams());
			cronTaskRegistrar.addCronTask(task1, task.getCron());
			// 便于观察
			String ddString = task.getName();
			concurrentHashMap.put(ddString , task1);	
			Thread.sleep(3000000);
			}
		}else {
			if (task.getState().equals("1")) {
				SchedulingRunnable task33 = new SchedulingRunnable(task.getKind(), task.getMethod(), task.getParams());
				cronTaskRegistrar.addCronTask(task33, task.getCron());
				// 便于观察
				concurrentHashMap.put(task.getName(), task33);
				taskDao.update(task);
				Thread.sleep(3000000);
			}else {
				taskDao.update(task);
			}
		}
	}
}
