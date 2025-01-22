package com.lawfirm.common.cache.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存同步工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheSyncUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    private static final String SYNC_LOCK_PREFIX = "cache:sync:";
    private static final long LOCK_WAIT_TIME = 3;
    private static final long LOCK_LEASE_TIME = 30;

    /**
     * 同步更新缓存
     * 使用分布式锁保证同一时间只有一个节点在更新缓存
     */
    public void syncUpdate(String key, Object value) {
        String lockKey = SYNC_LOCK_PREFIX + key;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    redisTemplate.opsForValue().set(key, value);
                    log.info("缓存同步更新成功, key: {}", key);
                } finally {
                    lock.unlock();
                }
            } else {
                log.warn("获取同步锁失败, key: {}", key);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("缓存同步更新被中断, key: {}", key, e);
        }
    }

    /**
     * 同步删除缓存
     */
    public void syncDelete(String key) {
        String lockKey = SYNC_LOCK_PREFIX + key;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    redisTemplate.delete(key);
                    log.info("缓存同步删除成功, key: {}", key);
                } finally {
                    lock.unlock();
                }
            } else {
                log.warn("获取同步锁失败, key: {}", key);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("缓存同步删除被中断, key: {}", key, e);
        }
    }

    /**
     * 同步更新缓存，先更新数据库再删除缓存
     * 使用分布式锁保证同一时间只有一个节点在更新
     */
    public void syncUpdateWithDb(String key, Supplier<Boolean> dbUpdate) {
        String lockKey = SYNC_LOCK_PREFIX + key;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    // 更新数据库
                    boolean success = dbUpdate.get();
                    if (success) {
                        // 删除缓存
                        redisTemplate.delete(key);
                        log.info("缓存同步更新成功(先更新数据库再删除缓存), key: {}", key);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                log.warn("获取同步锁失败, key: {}", key);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("缓存同步更新被中断, key: {}", key, e);
        }
    }
} 