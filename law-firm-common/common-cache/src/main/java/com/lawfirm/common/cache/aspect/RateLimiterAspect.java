package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流切面
 * 基础设施实现，不包含业务逻辑
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {

    private final RedissonClient redissonClient;

    @Pointcut("@annotation(com.lawfirm.common.cache.annotation.RateLimiter)")
    public void rateLimiterPointcut() {
    }

    @Before("rateLimiterPointcut()")
    public void doBefore(JoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        
        if (rateLimiter != null) {
            String key = rateLimiter.key();
            if (key.isEmpty()) {
                key = method.getDeclaringClass().getName() + "." + method.getName();
            }

            RRateLimiter limiter = redissonClient.getRateLimiter(key);
            
            // 尝试初始化限流器
            boolean initialized = limiter.trySetRate(
                org.redisson.api.RateType.OVERALL,
                rateLimiter.rate(),
                rateLimiter.rateInterval(),
                rateLimiter.rateIntervalUnit()
            );

            if (!initialized) {
                throw new IllegalStateException("无法初始化限流器");
            }

            // 尝试获取令牌
            if (!limiter.tryAcquire()) {
                throw new IllegalStateException("请求过于频繁");
            }
        }
    }
} 