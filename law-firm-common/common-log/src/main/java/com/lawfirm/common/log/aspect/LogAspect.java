package com.lawfirm.common.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 通用日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    /**
     * 配置切入点
     */
    @Pointcut("execution(* com.lawfirm..*.controller..*.*(..))")
    public void logPointcut() {
    }

    /**
     * 环绕通知
     */
    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始时间
        long startTime = System.currentTimeMillis();

        // 获取请求信息
        HttpServletRequest request = ServletUtils.getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String params = Arrays.toString(joinPoint.getArgs());

        try {
            log.info("开始请求 - URI: {}, Method: {}, Params: {}", requestURI, method, params);
            Object result = joinPoint.proceed();
            long timeCost = System.currentTimeMillis() - startTime;
            log.info("结束请求 - URI: {}, Method: {}, TimeCost: {}ms, Result: {}", 
                    requestURI, method, timeCost, objectMapper.writeValueAsString(result));
            return result;
        } catch (Exception e) {
            long timeCost = System.currentTimeMillis() - startTime;
            log.error("请求异常 - URI: {}, Method: {}, TimeCost: {}ms, Error: {}", 
                    requestURI, method, timeCost, e.getMessage());
            throw e;
        }
    }
} 