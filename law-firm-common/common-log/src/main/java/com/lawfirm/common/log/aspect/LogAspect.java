package com.lawfirm.common.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.util.LogUtils;
import com.lawfirm.common.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 通用日志切面
 */
@Slf4j
@Aspect
@Component("commonLogAspect")
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;
    private final LogProperties logProperties;
    private final ThreadPoolTaskExecutor asyncLogExecutor;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

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
        // 如果禁用了方法日志，直接执行方法
        if (!logProperties.isEnableMethodLog()) {
            return joinPoint.proceed();
        }

        // 获取请求信息
        HttpServletRequest request = ServletUtils.getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 检查是否需要排除该路径
        if (isPathExcluded(requestURI)) {
            return joinPoint.proceed();
        }

        // 开始时间
        long startTime = System.currentTimeMillis();

        // 处理请求参数
        String params = logProperties.isLogRequestParams() 
            ? LogUtils.desensitize(Arrays.toString(joinPoint.getArgs()), logProperties.getExcludeParamFields())
            : "已关闭参数记录";

        try {
            // 记录请求开始
            if (logProperties.isEnableAsyncLog()) {
                asyncLogExecutor.execute(() -> 
                    log.info("开始请求 - URI: {}, Method: {}, Params: {}", requestURI, method, params));
            } else {
                log.info("开始请求 - URI: {}, Method: {}, Params: {}", requestURI, method, params);
            }

            // 执行方法
            Object result = joinPoint.proceed();

            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;

            // 记录请求结束
            if (logProperties.isEnableAsyncLog()) {
                String resultStr = logProperties.isLogResponseBody() 
                    ? objectMapper.writeValueAsString(result) 
                    : "已关闭响应记录";
                asyncLogExecutor.execute(() -> 
                    log.info("结束请求 - URI: {}, Method: {}, TimeCost: {}ms, Result: {}", 
                            requestURI, method, timeCost, resultStr));
            } else {
                log.info("结束请求 - URI: {}, Method: {}, TimeCost: {}ms, Result: {}", 
                        requestURI, method, timeCost, 
                        logProperties.isLogResponseBody() ? objectMapper.writeValueAsString(result) : "已关闭响应记录");
            }

            return result;
        } catch (Exception e) {
            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;

            // 记录异常信息
            if (logProperties.isEnableAsyncLog()) {
                asyncLogExecutor.execute(() -> 
                    log.error("请求异常 - URI: {}, Method: {}, TimeCost: {}ms, Error: {}", 
                            requestURI, method, timeCost, 
                            logProperties.isLogStackTrace() ? LogUtils.formatException(e) : e.getMessage()));
            } else {
                log.error("请求异常 - URI: {}, Method: {}, TimeCost: {}ms, Error: {}", 
                        requestURI, method, timeCost, 
                        logProperties.isLogStackTrace() ? LogUtils.formatException(e) : e.getMessage());
            }

            throw e;
        }
    }

    /**
     * 检查路径是否需要排除
     */
    private boolean isPathExcluded(String path) {
        String[] excludePaths = logProperties.getExcludePaths();
        if (excludePaths == null || excludePaths.length == 0) {
            return false;
        }
        for (String pattern : excludePaths) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
} 