package com.lawfirm.common.cache.utils;

import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存防护工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheGuardUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;
    private RBloomFilter<String> bloomFilter;

    private static final String BLOOM_FILTER_NAME = "cache:bloom_filter";
    private static final String MUTEX_KEY_PREFIX = "cache:mutex:";
    private static final long MUTEX_EXPIRE_TIME = 10;
    private static final int RANDOM_EXPIRE_TIME = 5;

    @PostConstruct
    public void init() {
        // 初始化布隆过滤器
        bloomFilter = redissonClient.getBloomFilter(BLOOM_FILTER_NAME);
        // 预计元素数量为10000，误判率为0.01
        bloomFilter.tryInit(10000, 0.01);
    }

    /**
     * 添加key到布隆过滤器
     */
    public void addToBloomFilter(String key) {
        bloomFilter.add(key);
    }

    /**
     * 防止缓存穿透的查询方法
     * 使用布隆过滤器 + 缓存空对象
     */
    public Object getWithPenetrationGuard(String key, Supplier<Object> dbFallback) {
        // 布隆过滤器判断key是否存在
        if (!bloomFilter.contains(key)) {
            return null;
        }

        // 查询缓存
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return value;
        }

        // 查询数据库
        value = dbFallback.get();
        if (value == null) {
            // 缓存空对象，防止缓存穿透
            redisTemplate.opsForValue().set(key, "", 5, TimeUnit.MINUTES);
            return null;
        }

        // 缓存结果
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    /**
     * 防止缓存击穿的查询方法
     * 使用分布式锁
     */
    public Object getWithBreakdownGuard(String key, Supplier<Object> dbFallback) {
        // 查询缓存
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return value;
        }

        // 获取分布式锁
        String mutexKey = MUTEX_KEY_PREFIX + key;
        try {
            boolean locked = redisTemplate.opsForValue().setIfAbsent(mutexKey, "1", MUTEX_EXPIRE_TIME, TimeUnit.SECONDS);
            if (!locked) {
                // 获取锁失败，说明其他线程正在重建缓存，等待一会再查询
                Thread.sleep(50);
                return getWithBreakdownGuard(key, dbFallback);
            }

            // 双重检查
            value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                return value;
            }

            // 查询数据库
            value = dbFallback.get();
            if (value == null) {
                throw new BusinessException("数据不存在");
            }

            // 缓存结果，添加随机过期时间防止雪崩
            long expireTime = getRandomExpireTime();
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            return value;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException("获取分布式锁被中断");
        } finally {
            // 释放锁
            redisTemplate.delete(mutexKey);
        }
    }

    /**
     * 获取随机过期时间，防止缓存雪崩
     */
    private long getRandomExpireTime() {
        return (long) (Math.random() * RANDOM_EXPIRE_TIME) + MUTEX_EXPIRE_TIME;
    }

    /**
     * 缓存预热
     */
    public void warmUp(String key, Supplier<Object> dataSupplier) {
        try {
            Object value = dataSupplier.get();
            if (value != null) {
                redisTemplate.opsForValue().set(key, value);
                addToBloomFilter(key);
                log.info("缓存预热成功, key: {}", key);
            }
        } catch (Exception e) {
            log.error("缓存预热失败, key: {}", key, e);
        }
    }

    /**
     * 缓存降级
     * 当缓存服务不可用时，使用本地缓存
     */
    public Object getWithFallback(String key, Supplier<Object> localCacheFallback) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis服务不可用，使用本地缓存, key: {}", key, e);
            return localCacheFallback.get();
        }
    }
} 