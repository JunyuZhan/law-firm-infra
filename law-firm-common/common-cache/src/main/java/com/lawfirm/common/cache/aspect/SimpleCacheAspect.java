package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.cache.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 简单缓存切面
 * 基础设施实现，不包含业务逻辑
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SimpleCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.lawfirm.common.cache.annotation.SimpleCache)")
    public void simpleCachePointcut() {
    }

    @Around("simpleCachePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SimpleCache simpleCache = method.getAnnotation(SimpleCache.class);

        // 构建缓存key
        String key = buildCacheKey(simpleCache, method, point.getArgs());

        try {
            // 尝试从缓存获取
            Object cachedValue = redisTemplate.opsForValue().get(key);
            if (cachedValue != null) {
                return cachedValue;
            }

            // 执行方法
            Object result = point.proceed();

            // 设置缓存
            if (result != null) {
                redisTemplate.opsForValue().set(key, result, simpleCache.timeout(), simpleCache.timeUnit());
            }

            return result;
        } catch (Exception e) {
            log.warn("Cache operation failed for key: {}", key);
            if (simpleCache.ignoreException()) {
                return point.proceed();
            }
            throw e;
        }
    }

    /**
     * 构建缓存key
     */
    private String buildCacheKey(SimpleCache simpleCache, Method method, Object[] args) {
        StringBuilder key = new StringBuilder(CacheConstants.SIMPLE_CACHE_PREFIX);

        // 添加自定义key
        if (StringUtils.hasText(simpleCache.key())) {
            key.append(simpleCache.key());
        }

        // 添加方法名
        if (simpleCache.useMethodName()) {
            key.append(":").append(method.getDeclaringClass().getSimpleName())
               .append(".").append(method.getName());
        }

        // 添加参数
        if (simpleCache.useParams() && args != null && args.length > 0) {
            key.append(":").append(Arrays.deepHashCode(args));
        }

        return key.toString();
    }
} 