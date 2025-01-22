package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.CacheWarmUp;
import com.lawfirm.common.cache.utils.CacheGuardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 缓存预热切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheWarmUpAspect {

    private final CacheGuardUtil cacheGuardUtil;

    @Pointcut("@annotation(com.lawfirm.common.cache.annotation.CacheWarmUp)")
    public void warmUpPointcut() {
    }

    @Around("warmUpPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CacheWarmUp warmUp = method.getAnnotation(CacheWarmUp.class);

        // 构建缓存键
        String key = buildCacheKey(warmUp, method, point.getArgs());

        // 执行方法
        Object result = point.proceed();

        // 预热缓存
        if (result != null) {
            cacheGuardUtil.warmUp(key, () -> result);
        }

        return result;
    }

    /**
     * 构建缓存键
     */
    private String buildCacheKey(CacheWarmUp warmUp, Method method, Object[] args) {
        StringBuilder key = new StringBuilder();

        // 添加前缀
        if (!warmUp.keyPrefix().isEmpty()) {
            key.append(warmUp.keyPrefix()).append(":");
        }

        // 添加方法名
        if (warmUp.useMethodName()) {
            key.append(method.getDeclaringClass().getSimpleName())
               .append(".")
               .append(method.getName())
               .append(":");
        }

        // 添加参数
        if (warmUp.useParams() && args != null && args.length > 0) {
            key.append(Arrays.toString(args));
        }

        return key.toString();
    }
} 