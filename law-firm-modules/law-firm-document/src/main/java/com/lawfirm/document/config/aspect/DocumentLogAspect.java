package com.lawfirm.document.config.aspect;

import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.util.LogUtils;
import com.lawfirm.common.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 文档模块日志切面
 * 用于记录文档相关的操作日志和审计日志
 */
@Slf4j
@Aspect
@Component("documentLogAspect")
@RequiredArgsConstructor
public class DocumentLogAspect {

    private final LogProperties logProperties;

    /**
     * 配置切入点
     */
    @Pointcut("execution(* com.lawfirm.document..*.controller..*.*(..))")
    public void documentLogPointcut() {
    }

    /**
     * 环绕通知
     */
    @Around("documentLogPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 如果禁用了方法日志，直接执行方法
        if (!logProperties.isEnableMethodLog()) {
            return joinPoint.proceed();
        }

        // 获取请求信息
        String requestURI = ServletUtils.getRequest().getRequestURI();
        String method = ServletUtils.getRequest().getMethod();

        // 开始时间
        long startTime = System.currentTimeMillis();

        try {
            // 记录请求开始
            log.info("开始请求 - URI: {}, Method: {}", requestURI, method);

            // 执行方法
            Object result = joinPoint.proceed();

            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;

            // 记录请求结束
            log.info("结束请求 - URI: {}, Method: {}, TimeCost: {}ms", 
                    requestURI, method, timeCost);

            return result;
        } catch (Exception e) {
            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;

            // 记录异常信息
            log.error("请求异常 - URI: {}, Method: {}, TimeCost: {}ms, Error: {}", 
                    requestURI, method, timeCost, 
                    logProperties.isLogStackTrace() ? LogUtils.formatException(e) : e.getMessage());

            throw e;
        }
    }
}
