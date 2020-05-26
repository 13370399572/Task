package com.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.redis.dao.UserDao;
import com.redis.entity.User;

@Service
public class userService {
	@Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<User> queryAll() {
        return userDao.queryAll();
    }
    /**
     * ��ȡ�û����ԣ��ȴӻ����л�ȡ�û���û����ȡ���ݱ��� ���ݣ��ٽ�����д�뻺��
     */
    public  User findUserById(int id) {
        String key = "user_" + id;

        ValueOperations<String, User> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            User user = operations.get(key);
            System.out.println("==========�ӻ����л������=========");
            System.out.println(user.getUserName());
            System.out.println("==============================");
            return user;
        } else {
            User user = userDao.findUserById(id);
            System.out.println("==========�����ݱ��л������=========");
            System.out.println(user.getUserName());
            System.out.println("==============================");

            // д�뻺��
            operations.set(key, user, 5, TimeUnit.HOURS);
            return user;
        }

    }

    /**
     * �����û����ԣ��ȸ������ݱ��ɹ�֮��ɾ��ԭ���Ļ��棬�ٸ��»���
     */
    public int updateUser(User user) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        int result = userDao.updateUser(user);
        if (result != 0) {
            String key = "user_" + user.getUid();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("ɾ�������е�key=========>" + key);
            }
            // �ٽ����º�����ݼ��뻺��
            User userNew = userDao.findUserById(user.getUid());
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    /**
     * ɾ���û����ԣ�ɾ�����ݱ������ݣ�Ȼ��ɾ������
     */
    public int deleteUserById(int id) {
        int result = userDao.deleteUserById(id);
        String key = "user_" + id;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("ɾ���˻����е�key:" + key);
            }
        }
        return result;
    }
    
    /**
     * ��ȡ�û����ԣ��ȴӻ����л�ȡ�û���û����ȡ���ݱ��� ���ݣ��ٽ�����д�뻺��
     */
	/*
	 * public User findUserById(int id) { String key = "user_" + id;
	 * 
	 * ValueOperations<String, User> operations = redisTemplate.opsForValue();
	 * 
	 * boolean hasKey = redisTemplate.hasKey(key); if (hasKey) { long start =
	 * System.currentTimeMillis(); User user = operations.get(key);
	 * System.out.println("==========�ӻ����л������=========");
	 * System.out.println(user.getUserName());
	 * System.out.println("=============================="); long end =
	 * System.currentTimeMillis(); System.out.println("��ѯredis���ѵ�ʱ����:" + (end -
	 * start)+"s"); return user; } else { long start = System.currentTimeMillis();
	 * User user = userDao.findUserById(id);
	 * System.out.println("==========�����ݱ��л������=========");
	 * System.out.println(user.getUserName());
	 * System.out.println("==============================");
	 * 
	 * // д�뻺�� operations.set(key, user, 5, TimeUnit.HOURS); long end =
	 * System.currentTimeMillis(); System.out.println("��ѯmysql���ѵ�ʱ����:" + (end -
	 * start)+"s"); return user; }
	 * 
	 * }
	 */
}
