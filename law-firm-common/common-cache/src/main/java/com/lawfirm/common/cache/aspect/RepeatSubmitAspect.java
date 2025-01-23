package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.RepeatSubmit;
import com.lawfirm.common.cache.constant.CacheConstants;
import com.lawfirm.common.cache.utils.CacheUtil;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.lawfirm.common.cache.utils.RedisLockUtil;

import java.lang.reflect.Method;

/**
 * 防重提交切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RepeatSubmitAspect {

    private final CacheUtil cacheUtil;
    private final RedisLockUtil redisLockUtil;

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
            if (cacheUtil.hasKey(key)) {
                log.warn("重复提交，key: {}", key);
                throw new BusinessException(repeatSubmit.message());
            }
            cacheUtil.set(key, "", repeatSubmit.interval(), repeatSubmit.timeUnit());
        }
    }

    /**
     * 构建缓存键
     */
    private String buildKey(Method method) {
        StringBuilder key = new StringBuilder(CacheConstants.REPEAT_SUBMIT_KEY);
        key.append(ServletUtils.getRequest().getRequestURI()).append(":");
        key.append(ServletUtils.getLoginUserId()).append(":");
        key.append(method.getDeclaringClass().getName()).append(".");
        key.append(method.getName());
        return key.toString();
    }
} 