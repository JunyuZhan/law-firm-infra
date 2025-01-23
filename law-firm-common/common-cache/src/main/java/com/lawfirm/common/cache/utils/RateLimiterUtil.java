package com.lawfirm.common.cache.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 限流工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimiterUtil {

    private final RedissonClient redissonClient;

    /**
     * 创建限流器
     *
     * @param key 限流key
     * @param rate 速率
     * @param rateInterval 速率间隔
     * @param rateIntervalUnit 速率间隔单位
     * @return 是否创建成功
     */
    public boolean tryCreateRateLimiter(String key, long rate, long rateInterval, RateIntervalUnit rateIntervalUnit) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        try {
            return rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, rateIntervalUnit);
        } catch (Exception e) {
            log.error("创建限流器失败，key: {}", key, e);
            return false;
        }
    }

    /**
     * 尝试获取令牌
     *
     * @param key 限流key
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key) {
        return tryAcquire(key, 1);
    }

    /**
     * 尝试获取令牌
     *
     * @param key 限流key
     * @param permits 令牌数量
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key, long permits) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        try {
            return rateLimiter.tryAcquire(permits);
        } catch (Exception e) {
            log.error("获取令牌失败，key: {}", key, e);
            return false;
        }
    }

    /**
     * 删除限流器
     *
     * @param key 限流key
     */
    public void remove(String key) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        try {
            rateLimiter.delete();
        } catch (Exception e) {
            log.error("删除限流器失败，key: {}", key, e);
        }
    }
} 