package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.RepeatSubmit;
import com.lawfirm.common.cache.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * 防重提交切面
 * 基础设施实现，不包含业务逻辑
 */
@Slf4j
@Aspect
@Component("commonRepeatSubmitAspect")
public class RepeatSubmitAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    
    public RepeatSubmitAspect(@Qualifier("commonCacheRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    // Lua脚本：如果key不存在则设置并返回1，否则返回0
    private static final String LOCK_SCRIPT = 
        "if redis.call('exists', KEYS[1]) == 0 then " +
        "   redis.call('setex', KEYS[1], ARGV[1], ARGV[2]) " +
        "   return 1 " +
        "else " +
        "   return 0 " +
        "end";

    @Pointcut("@annotation(com.lawfirm.common.cache.annotation.RepeatSubmit)")
    public void repeatSubmitPointCut() {
    }

    @Before("repeatSubmitPointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RepeatSubmit repeatSubmit = method.getAnnotation(RepeatSubmit.class);
        
        if (repeatSubmit != null) {
            String key = buildKey(method);
            long timeout = repeatSubmit.timeUnit().toSeconds(repeatSubmit.interval());
            
            // 使用Lua脚本保证原子性
            Long result = redisTemplate.execute(
                RedisScript.of(LOCK_SCRIPT, Long.class),
                Collections.singletonList(key),
                timeout,
                "1"
            );
            
            if (result == null || result == 0) {
                throw new IllegalStateException("请勿重复提交");
            }
        }
    }

    /**
     * 构建缓存键
     */
    private String buildKey(Method method) {
        return CacheConstants.REPEAT_SUBMIT_KEY +
               method.getDeclaringClass().getName() +
               "." +
               method.getName();
    }
} 