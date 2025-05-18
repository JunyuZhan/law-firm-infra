package com.lawfirm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁服务
 * 提供基于Redis的分布式锁功能
 */
@Service("apiDistributedLockService")
@Slf4j
@RequiredArgsConstructor
public class DistributedLockService {

    private final RedissonClient redissonClient;
    
    private static final String LOCK_KEY_PREFIX = "distributed_lock:";
    
    /**
     * 尝试获取锁并执行操作
     *
     * @param lockKey 锁的key
     * @param waitTime 等待锁的最长时间
     * @param leaseTime 持有锁的时间
     * @param timeUnit 时间单位
     * @param supplier 获取锁后要执行的操作
     * @param <T> 返回值类型
     * @return 操作结果
     */
    public <T> T executeWithLock(String lockKey, long waitTime, long leaseTime, 
                                 TimeUnit timeUnit, Supplier<T> supplier) {
        String fullLockKey = LOCK_KEY_PREFIX + lockKey;
        RLock lock = redissonClient.getLock(fullLockKey);
        
        boolean locked = false;
        try {
            log.debug("尝试获取分布式锁: {}", fullLockKey);
            locked = lock.tryLock(waitTime, leaseTime, timeUnit);
            
            if (locked) {
                log.debug("成功获取分布式锁: {}", fullLockKey);
                return supplier.get();
            } else {
                log.warn("获取分布式锁失败: {}", fullLockKey);
                throw new RuntimeException("操作频繁，请稍后再试");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("获取分布式锁被中断: {}", fullLockKey, e);
            throw new RuntimeException("操作被中断，请重试");
        } finally {
            if (locked) {
                try {
                    lock.unlock();
                    log.debug("释放分布式锁: {}", fullLockKey);
                } catch (Exception e) {
                    log.error("释放分布式锁异常: {}", fullLockKey, e);
                }
            }
        }
    }
    
    /**
     * 使用默认配置执行加锁操作
     *
     * @param lockKey 锁的key
     * @param supplier 获取锁后要执行的操作
     * @param <T> 返回值类型
     * @return 操作结果
     */
    public <T> T executeWithLock(String lockKey, Supplier<T> supplier) {
        return executeWithLock(lockKey, 5, 30, TimeUnit.SECONDS, supplier);
    }
    
    /**
     * 执行无返回值的加锁操作
     *
     * @param lockKey 锁的key
     * @param waitTime 等待锁的最长时间
     * @param leaseTime 持有锁的时间
     * @param timeUnit 时间单位
     * @param runnable 获取锁后要执行的操作
     */
    public void executeWithLock(String lockKey, long waitTime, long leaseTime, 
                               TimeUnit timeUnit, Runnable runnable) {
        executeWithLock(lockKey, waitTime, leaseTime, timeUnit, () -> {
            runnable.run();
            return null;
        });
    }
    
    /**
     * 使用默认配置执行无返回值的加锁操作
     *
     * @param lockKey 锁的key
     * @param runnable 获取锁后要执行的操作
     */
    public void executeWithLock(String lockKey, Runnable runnable) {
        executeWithLock(lockKey, 5, 30, TimeUnit.SECONDS, runnable);
    }
} 