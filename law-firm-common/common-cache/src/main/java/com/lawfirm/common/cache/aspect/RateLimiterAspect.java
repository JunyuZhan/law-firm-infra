package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.RateLimiter;
import com.lawfirm.common.cache.utils.RateLimiterUtil;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流切面
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    @Autowired
    private RateLimiterUtil rateLimiterUtil;

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

            // 创建限流器
            boolean created = rateLimiterUtil.tryCreateRateLimiter(key,
                    rateLimiter.rate(),
                    rateLimiter.rateInterval(),
                    rateLimiter.rateIntervalUnit());

            if (!created) {
                log.error("创建限流器失败，key: {}", key);
                throw new BusinessException("系统繁忙，请稍后重试");
            }

            // 尝试获取令牌
            boolean acquired = rateLimiterUtil.tryAcquire(key);
            if (!acquired) {
                log.warn("请求被限流，key: {}", key);
                throw new BusinessException(rateLimiter.message());
            }
        }
    }
} 