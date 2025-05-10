package com.lawfirm.document.config.aspect;

import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.cache.config.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 文档模块缓存切面
 * 用于处理文档相关的缓存操作
 */
@Slf4j
@Aspect
@Component
public class CacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheProperties cacheProperties;
    
    public CacheAspect(
            @Qualifier("cacheRedisTemplate") RedisTemplate<String, Object> redisTemplate,
            @Qualifier("appCacheProperties") CacheProperties cacheProperties) {
        this.redisTemplate = redisTemplate;
        this.cacheProperties = cacheProperties;
    }

    /**
     * 配置切入点
     */
    @Pointcut("execution(* com.lawfirm.document..*.service..*.*(..))")
    public void cachePointcut() {
    }

    /**
     * 环绕通知
     */
    @Around("cachePointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 如果禁用了缓存，直接执行方法
        if (!cacheProperties.isEnabled()) {
            return joinPoint.proceed();
        }

        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 构建缓存key
        String key = buildCacheKey(className, methodName, joinPoint.getArgs());

        try {
            // 尝试从缓存获取
            Object cachedValue = redisTemplate.opsForValue().get(key);
            if (cachedValue != null) {
                log.debug("从缓存获取数据 - Key: {}", key);
                return cachedValue;
            }

            // 执行方法
            Object result = joinPoint.proceed();

            // 设置缓存
            if (result != null) {
                redisTemplate.opsForValue().set(key, result, 
                    cacheProperties.getExpiration(), TimeUnit.MINUTES);
                log.debug("设置缓存数据 - Key: {}", key);
            }

            return result;
        } catch (Exception e) {
            log.warn("缓存操作失败 - Key: {}, Error: {}", key, e.getMessage());
            return joinPoint.proceed();
        }
    }

    /**
     * 构建缓存key
     */
    private String buildCacheKey(String className, String methodName, Object[] args) {
        StringBuilder key = new StringBuilder("document:");
        key.append(className).append(":").append(methodName);

        if (args != null && args.length > 0) {
            key.append(":").append(Arrays.deepHashCode(args));
        }

        return key.toString();
    }
}
