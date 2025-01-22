package com.lawfirm.common.security.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.security.annotation.BehaviorLog;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 行为日志切面
 */
@Slf4j
@Aspect
@Component
public class BehaviorLogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.lawfirm.common.security.annotation.BehaviorLog)")
    public void behaviorLogPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param result    返回值
     */
    @AfterReturning(pointcut = "behaviorLogPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e        异常
     */
    @AfterThrowing(value = "behaviorLogPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object result) {
        try {
            // 获得注解
            BehaviorLog behaviorLog = getBehaviorLog(joinPoint);
            if (behaviorLog == null) {
                return;
            }

            // 获取当前的用户
            String username = SecurityUtils.getUsername();

            // 获取请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            Map<String, Object> logData = new HashMap<>();
            logData.put("module", behaviorLog.module());
            logData.put("type", behaviorLog.type());
            logData.put("description", behaviorLog.description());
            logData.put("username", username);
            logData.put("requestMethod", request.getMethod());
            logData.put("requestUrl", request.getRequestURI());
            logData.put("method", className + "." + methodName + "()");
            logData.put("ip", getIpAddress(request));
            logData.put("operateTime", new Date());

            if (e != null) {
                logData.put("status", "ERROR");
                logData.put("errorMessage", e.getMessage());
            } else {
                logData.put("status", "SUCCESS");
            }

            // 是否需要保存请求参数
            if (behaviorLog.saveRequestData()) {
                setRequestData(joinPoint, logData);
            }
            // 是否需要保存响应数据
            if (behaviorLog.saveResponseData() && result != null) {
                logData.put("response", objectMapper.writeValueAsString(result));
            }

            // TODO: 保存日志到数据库或其他存储
            log.info("行为日志：{}", objectMapper.writeValueAsString(logData));
        } catch (Exception exp) {
            log.error("记录行为日志异常", exp);
        }
    }

    /**
     * 获取注解
     */
    private BehaviorLog getBehaviorLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(BehaviorLog.class);
    }

    /**
     * 获取请求参数
     */
    private void setRequestData(JoinPoint joinPoint, Map<String, Object> logData) {
        try {
            String params = objectMapper.writeValueAsString(joinPoint.getArgs());
            logData.put("request", params);
        } catch (Exception e) {
            log.error("解析请求参数异常", e);
        }
    }

    /**
     * 获取IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
} 