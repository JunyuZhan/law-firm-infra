package com.lawfirm.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * API性能监控切面
 * 记录API层方法执行时间，监控性能瓶颈
 */
@Aspect
@Component("apiPerformanceMonitorAspect")
@Slf4j
public class PerformanceMonitorAspect {

    // 性能警告阈值：500毫秒
    private static final long PERFORMANCE_THRESHOLD_MS = 500;

    /**
     * 定义控制器切点
     */
    @Pointcut("execution(* com.lawfirm.api.controller.*.*(..))")
    public void apiControllerPoint() {}

    /**
     * 环绕通知，记录方法执行时间
     */
    @Around("apiControllerPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取目标方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        
        // 类名
        String className = point.getTarget().getClass().getSimpleName();
        // 方法名
        String methodName = method.getName();
        
        // 开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            // 执行目标方法
            return point.proceed();
        } finally {
            // 结束时间
            long endTime = System.currentTimeMillis();
            // 执行时间
            long executeTime = endTime - startTime;
            
            // 记录执行时间
            if (executeTime >= PERFORMANCE_THRESHOLD_MS) {
                // 执行时间超过阈值，记录警告日志
                log.warn("[性能监控] {}#{} 执行时间: {}ms - 超过阈值({}ms)", 
                        className, methodName, executeTime, PERFORMANCE_THRESHOLD_MS);
            } else {
                // 正常执行时间，记录调试日志
                log.debug("[性能监控] {}#{} 执行时间: {}ms", 
                        className, methodName, executeTime);
            }
        }
    }
} 