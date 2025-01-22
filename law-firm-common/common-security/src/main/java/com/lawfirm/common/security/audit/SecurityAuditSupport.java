package com.lawfirm.common.security.audit;

import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 安全审计支持
 */
@Slf4j
@Component
public class SecurityAuditSupport {

    /**
     * Redis Key前缀
     */
    private static final String REDIS_KEY_PREFIX = "security:audit:";

    /**
     * 登录失败次数限制
     */
    private static final int LOGIN_FAIL_LIMIT = 5;

    /**
     * 锁定时间（分钟）
     */
    private static final int LOCK_TIME_MINUTES = 30;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 记录登录失败
     *
     * @param username 用户名
     * @param ip      IP地址
     * @return 是否需要锁定账户
     */
    public boolean recordLoginFailure(String username, String ip) {
        String key = REDIS_KEY_PREFIX + "login:fail:" + username;
        String lockKey = REDIS_KEY_PREFIX + "login:lock:" + username;

        // 如果账户已被锁定
        if (Boolean.TRUE.equals(redisTemplate.hasKey(lockKey))) {
            log.warn("账户[{}]已被锁定", username);
            return true;
        }

        // 记录失败信息
        Map<String, Object> failInfo = new HashMap<>();
        failInfo.put("ip", ip);
        failInfo.put("time", new Date());
        redisTemplate.opsForList().rightPush(key, failInfo);
        redisTemplate.expire(key, 24, TimeUnit.HOURS);

        // 获取失败次数
        Long failCount = redisTemplate.opsForList().size(key);
        if (failCount != null && failCount >= LOGIN_FAIL_LIMIT) {
            // 锁定账户
            redisTemplate.opsForValue().set(lockKey, new Date(), LOCK_TIME_MINUTES, TimeUnit.MINUTES);
            log.warn("账户[{}]因登录失败次数过多被锁定", username);
            return true;
        }

        return false;
    }

    /**
     * 检查账户是否被锁定
     *
     * @param username 用户名
     * @return 是否被锁定
     */
    public boolean isAccountLocked(String username) {
        String lockKey = REDIS_KEY_PREFIX + "login:lock:" + username;
        return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey));
    }

    /**
     * 清除登录失败记录
     *
     * @param username 用户名
     */
    public void clearLoginFailures(String username) {
        String key = REDIS_KEY_PREFIX + "login:fail:" + username;
        String lockKey = REDIS_KEY_PREFIX + "login:lock:" + username;
        redisTemplate.delete(key);
        redisTemplate.delete(lockKey);
    }

    /**
     * 记录敏感操作
     *
     * @param operation 操作类型
     * @param details   操作详情
     */
    public void recordSensitiveOperation(String operation, Map<String, Object> details) {
        String username = SecurityUtils.getUsername();
        String key = REDIS_KEY_PREFIX + "sensitive:" + username;

        Map<String, Object> auditInfo = new HashMap<>(details);
        auditInfo.put("operation", operation);
        auditInfo.put("time", new Date());
        auditInfo.put("username", username);

        redisTemplate.opsForList().rightPush(key, auditInfo);
        redisTemplate.expire(key, 90, TimeUnit.DAYS); // 保存90天

        log.info("记录敏感操作：用户[{}]执行[{}]操作", username, operation);
    }

    /**
     * 记录权限变更
     *
     * @param targetUser 目标用户
     * @param operation  变更操作
     * @param details    变更详情
     */
    public void recordPermissionChange(String targetUser, String operation, Map<String, Object> details) {
        String username = SecurityUtils.getUsername();
        String key = REDIS_KEY_PREFIX + "permission:" + targetUser;

        Map<String, Object> auditInfo = new HashMap<>(details);
        auditInfo.put("operation", operation);
        auditInfo.put("time", new Date());
        auditInfo.put("operator", username);
        auditInfo.put("targetUser", targetUser);

        redisTemplate.opsForList().rightPush(key, auditInfo);
        redisTemplate.expire(key, 90, TimeUnit.DAYS); // 保存90天

        log.info("记录权限变更：操作员[{}]对用户[{}]执行[{}]操作", username, targetUser, operation);
    }
} 