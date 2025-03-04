package com.lawfirm.core.ai.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.util.LogUtils;
import com.lawfirm.core.ai.utils.AIUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * AI操作日志切面
 * 继承通用日志功能，增加AI特有的日志处理
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AILogAspect {
    
    private final ObjectMapper objectMapper;
    private final LogProperties logProperties;
    private final ThreadPoolTaskExecutor asyncLogExecutor;
    
    /**
     * AI服务切入点
     */
    @Pointcut("execution(* com.lawfirm.core.ai.service..*.*(..))")
    public void aiServicePointcut() {
    }
    
    /**
     * AI处理器切入点
     */
    @Pointcut("execution(* com.lawfirm.core.ai.handler..*.*(..))")
    public void aiHandlerPointcut() {
    }
    
    /**
     * AI服务环绕通知
     */
    @Around("aiServicePointcut()")
    public Object doAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        return doAround(joinPoint, "AI服务");
    }
    
    /**
     * AI处理器环绕通知
     */
    @Around("aiHandlerPointcut()")
    public Object doAroundHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        return doAround(joinPoint, "AI处理器");
    }
    
    /**
     * 环绕通知实现
     */
    private Object doAround(ProceedingJoinPoint joinPoint, String operationType) throws Throwable {
        // 如果禁用了方法日志，直接执行方法
        if (!logProperties.isEnableMethodLog()) {
            return joinPoint.proceed();
        }
        
        // 生成操作ID
        String operationId = AIUtils.generateOperationId();
        
        // 获取方法信息
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 处理请求参数
        String params = logProperties.isLogRequestParams()
            ? LogUtils.desensitize(Arrays.toString(joinPoint.getArgs()), logProperties.getExcludeParamFields())
            : "已关闭参数记录";
        
        // 开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            // 记录开始日志
            if (logProperties.isEnableAsyncLog()) {
                asyncLogExecutor.execute(() ->
                    log.info("[{}] {} 开始调用 - 操作: {}.{}, 参数: {}",
                            operationId, operationType, className, methodName, params));
            } else {
                log.info("[{}] {} 开始调用 - 操作: {}.{}, 参数: {}",
                        operationId, operationType, className, methodName, params);
            }
            
            // 执行方法
            Object result = joinPoint.proceed();
            
            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;
            
            // 记录成功日志
            if (logProperties.isEnableAsyncLog()) {
                String resultStr = logProperties.isLogResponseBody()
                    ? objectMapper.writeValueAsString(result)
                    : "已关闭响应记录";
                asyncLogExecutor.execute(() ->
                    log.info("[{}] {} 调用成功 - 操作: {}.{}, 耗时: {}ms, 结果: {}",
                            operationId, operationType, className, methodName, timeCost, resultStr));
            } else {
                log.info("[{}] {} 调用成功 - 操作: {}.{}, 耗时: {}ms, 结果: {}",
                        operationId, operationType, className, methodName, timeCost,
                        logProperties.isLogResponseBody() ? objectMapper.writeValueAsString(result) : "已关闭响应记录");
            }
            
            return result;
        } catch (Exception e) {
            // 计算执行时间
            long timeCost = System.currentTimeMillis() - startTime;
            
            // 记录异常日志
            if (logProperties.isEnableAsyncLog()) {
                asyncLogExecutor.execute(() ->
                    log.error("[{}] {} 调用异常 - 操作: {}.{}, 耗时: {}ms, 异常: {}",
                            operationId, operationType, className, methodName, timeCost,
                            logProperties.isLogStackTrace() ? LogUtils.formatException(e) : e.getMessage()));
            } else {
                log.error("[{}] {} 调用异常 - 操作: {}.{}, 耗时: {}ms, 异常: {}",
                        operationId, operationType, className, methodName, timeCost,
                        logProperties.isLogStackTrace() ? LogUtils.formatException(e) : e.getMessage());
            }
            
            // 构建错误信息
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("operationId", operationId);
            errorInfo.put("operationType", operationType);
            errorInfo.put("className", className);
            errorInfo.put("methodName", methodName);
            errorInfo.put("timeCost", timeCost);
            errorInfo.put("error", e.getMessage());
            errorInfo.put("stackTrace", logProperties.isLogStackTrace() ? LogUtils.formatException(e) : null);
            
            // 发布错误事件（如果需要）
            // TODO: 可以在这里发布错误事件，触发告警等机制
            
            throw e;
        }
    }
} 