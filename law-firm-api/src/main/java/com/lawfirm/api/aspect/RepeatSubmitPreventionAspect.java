package com.lawfirm.api.aspect;

import com.lawfirm.api.annotation.RepeatSubmitPrevention;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交切面
 * 基于Redis实现防重复提交功能
 */
@Aspect
@Component("apiRepeatSubmitPreventionAspect")
@Slf4j
@RequiredArgsConstructor
public class RepeatSubmitPreventionAspect {

    private final RedissonClient redissonClient;
    
    private static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    @Pointcut("@annotation(com.lawfirm.api.annotation.RepeatSubmitPrevention)")
    public void repeatSubmitPointcut() {
    }

    @Around("repeatSubmitPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return point.proceed();
        }
        
        HttpServletRequest request = attributes.getRequest();
        
        // 获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RepeatSubmitPrevention preventionAnnotation = method.getAnnotation(RepeatSubmitPrevention.class);
        
        if (preventionAnnotation == null) {
            return point.proceed();
        }
        
        // 构建防重复提交的key
        String submitKey = buildRepeatSubmitKey(request, point, preventionAnnotation);
        
        // 获取Redis缓存
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(REPEAT_SUBMIT_KEY);
        
        // 检查是否存在重复提交
        if (mapCache.containsKey(submitKey)) {
            log.warn("检测到重复提交: {}", submitKey);
            throw new BusinessException(preventionAnnotation.message());
        }
        
        // 设置防重复标记
        mapCache.put(
            submitKey, 
            System.currentTimeMillis(), 
            preventionAnnotation.interval(), 
            preventionAnnotation.timeUnit()
        );
        
        // 执行原方法
        return point.proceed();
    }
    
    /**
     * 构建防重复提交的key
     */
    private String buildRepeatSubmitKey(HttpServletRequest request, ProceedingJoinPoint point, RepeatSubmitPrevention prevention) {
        StringBuilder sb = new StringBuilder();
        
        // 添加类名和方法名
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        sb.append(className).append(".").append(methodName).append(":");
        
        // 添加用户标识或会话标识
        if (prevention.userBased()) {
            // 基于用户隔离
            Long userId = SecurityUtils.getUserId();
            sb.append("user:").append(userId == null ? "anonymous" : userId);
        } else {
            // 基于会话隔离
            String sessionId = request.getSession().getId();
            sb.append("session:").append(sessionId);
        }
        
        // 添加请求参数（可选）
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            String params = Arrays.toString(args);
            // 使用MD5处理参数，避免key过长
            String paramsMd5 = DigestUtils.md5DigestAsHex(params.getBytes());
            sb.append(":").append(paramsMd5);
        }
        
        return sb.toString();
    }
} 