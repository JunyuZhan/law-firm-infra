package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.config.LoginConfig;
import com.lawfirm.auth.service.LoginLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录限制服务实现类
 */
@Service
public class LoginLimitServiceImpl implements LoginLimitService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LoginConfig loginConfig;

    private static final String LOGIN_FAIL_COUNT_KEY = "login:fail:count:";
    private static final String LOGIN_LOCK_KEY = "login:lock:";
    private static final String IP_COUNT_KEY = "login:ip:count:";
    private static final String IP_LIMIT_KEY = "login:ip:limit:";

    @Override
    public void recordLoginFail(String username, String ip) {
        // 记录用户登录失败次数
        String userFailKey = LOGIN_FAIL_COUNT_KEY + username;
        Long failCount = redisTemplate.opsForValue().increment(userFailKey);
        if (failCount == 1) {
            redisTemplate.expire(userFailKey, loginConfig.getLockTime(), TimeUnit.MINUTES);
        }

        // 达到最大失败次数，锁定用户
        if (failCount >= loginConfig.getMaxFailCount()) {
            String lockKey = LOGIN_LOCK_KEY + username;
            redisTemplate.opsForValue().set(lockKey, true, loginConfig.getLockTime(), TimeUnit.MINUTES);
        }

        // 记录IP登录次数
        if (loginConfig.isIpLimit()) {
            String ipCountKey = IP_COUNT_KEY + ip;
            Long ipCount = redisTemplate.opsForValue().increment(ipCountKey);
            if (ipCount == 1) {
                redisTemplate.expire(ipCountKey, loginConfig.getIpLimitTime(), TimeUnit.MINUTES);
            }

            // 达到IP最大次数，限制IP
            if (ipCount >= loginConfig.getIpMaxCount()) {
                String ipLimitKey = IP_LIMIT_KEY + ip;
                redisTemplate.opsForValue().set(ipLimitKey, true, loginConfig.getIpLimitTime(), TimeUnit.MINUTES);
            }
        }
    }

    @Override
    public void recordLoginSuccess(String username, String ip) {
        // 清除用户登录失败记录
        clearUserLimit(username);
        
        // 如果开启了IP限制，记录IP登录次数
        if (loginConfig.isIpLimit()) {
            String ipCountKey = IP_COUNT_KEY + ip;
            Long ipCount = redisTemplate.opsForValue().increment(ipCountKey);
            if (ipCount == 1) {
                redisTemplate.expire(ipCountKey, loginConfig.getIpLimitTime(), TimeUnit.MINUTES);
            }
        }
    }

    @Override
    public boolean isUserLocked(String username) {
        String lockKey = LOGIN_LOCK_KEY + username;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(lockKey));
    }

    @Override
    public boolean isIpLimited(String ip) {
        if (!loginConfig.isIpLimit()) {
            return false;
        }
        String ipLimitKey = IP_LIMIT_KEY + ip;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(ipLimitKey));
    }

    @Override
    public void clearUserLimit(String username) {
        String userFailKey = LOGIN_FAIL_COUNT_KEY + username;
        String lockKey = LOGIN_LOCK_KEY + username;
        redisTemplate.delete(userFailKey);
        redisTemplate.delete(lockKey);
    }

    @Override
    public void clearIpLimit(String ip) {
        if (loginConfig.isIpLimit()) {
            String ipCountKey = IP_COUNT_KEY + ip;
            String ipLimitKey = IP_LIMIT_KEY + ip;
            redisTemplate.delete(ipCountKey);
            redisTemplate.delete(ipLimitKey);
        }
    }
} 