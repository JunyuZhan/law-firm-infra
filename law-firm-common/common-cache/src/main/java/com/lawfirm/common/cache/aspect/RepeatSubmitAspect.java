package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.RepeatSubmit;
import com.lawfirm.common.cache.constant.CacheConstants;
import com.lawfirm.common.cache.utils.CacheUtil;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.utils.ServletUtils;
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
 * 防重提交切面
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {

    @Autowired
    private CacheUtil cacheUtil;

    @Pointcut("@annotation(com.lawfirm.common.cache.annotation.RepeatSubmit)")
    public void repeatSubmitPointcut() {
    }

    @Before("repeatSubmitPointcut()")
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