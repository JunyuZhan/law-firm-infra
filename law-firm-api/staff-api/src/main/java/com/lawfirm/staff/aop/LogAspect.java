package com.lawfirm.staff.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 操作日志记录处理
 * 
 * @author weidi
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Pointcut("execution(* com.lawfirm.staff.controller..*.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        
        // 执行方法
        Object result = point.proceed();
        
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        
        // 记录日志
        recordLog(point, time, result);
        
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        // 获取请求相关信息
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        log.info("请求URI: {}", requestURI);
        log.info("请求方式: {}", method);
        log.info("请求类名: {}", className);
        log.info("请求方法: {}", methodName);
        log.info("请求参数: {}", Arrays.toString(args));
        log.info("执行时间: {} ms", time);
        try {
            log.info("返回结果: {}", objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            log.error("记录日志异常", e);
        }
    }
} 