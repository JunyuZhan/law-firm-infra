package com.lawfirm.api.aspect;

import com.lawfirm.api.annotation.Idempotent;
import com.lawfirm.common.core.exception.BusinessException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 幂等性控制切面
 * 基于Redis实现幂等性控制
 */
@Aspect
@Component("apiIdempotentAspect")
@Slf4j
@RequiredArgsConstructor
public class IdempotentAspect {

    private final RedissonClient redissonClient;
    
    private static final String IDEMPOTENT_KEY_PREFIX = "idempotent:";

    @Pointcut("@annotation(com.lawfirm.api.annotation.Idempotent)")
    public void idempotentPointcut() {
    }

    @Around("idempotentPointcut()")
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
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        
        if (idempotent == null) {
            return point.proceed();
        }
        
        // 获取幂等性标识
        String token = getIdempotentToken(request, idempotent);
        
        // 如果没有找到幂等性标识且不自动生成，则抛出异常
        if (!StringUtils.hasText(token) && !idempotent.autoGenerate()) {
            log.warn("未找到幂等性标识");
            throw new BusinessException("请求缺少幂等性标识");
        }
        
        // 如果需要自动生成幂等性标识
        if (!StringUtils.hasText(token) && idempotent.autoGenerate()) {
            token = generateIdempotentToken();
            log.debug("自动生成幂等性标识: {}", token);
        }
        
        // 构建幂等性键
        String idempotentKey = buildIdempotentKey(token, method);
        
        // 获取Redis缓存
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(IDEMPOTENT_KEY_PREFIX);
        
        // 检查是否存在幂等性标识
        if (mapCache.containsKey(idempotentKey)) {
            log.warn("检测到重复请求: {}", idempotentKey);
            throw new BusinessException(idempotent.message());
        }
        
        // 设置幂等性标记
        mapCache.put(
            idempotentKey, 
            System.currentTimeMillis(), 
            idempotent.expireTime(), 
            idempotent.timeUnit()
        );
        
        try {
            // 执行原方法
            return point.proceed();
        } catch (Exception e) {
            // 如果执行失败，则删除幂等性标记，允许重试
            mapCache.remove(idempotentKey);
            throw e;
        }
    }
    
    /**
     * 获取幂等性标识
     */
    private String getIdempotentToken(HttpServletRequest request, Idempotent idempotent) {
        String token = null;
        
        switch (idempotent.type()) {
            case HEADER:
                token = request.getHeader(idempotent.name());
                break;
            case PARAMETER:
                token = request.getParameter(idempotent.name());
                break;
            case BODY:
                // 从请求体获取需要特殊处理，暂不实现
                log.warn("暂不支持从请求体获取幂等性标识");
                break;
            default:
                break;
        }
        
        return token;
    }
    
    /**
     * 生成幂等性标识
     */
    private String generateIdempotentToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 构建幂等性键
     */
    private String buildIdempotentKey(String token, Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return className + ":" + methodName + ":" + token;
    }
} 