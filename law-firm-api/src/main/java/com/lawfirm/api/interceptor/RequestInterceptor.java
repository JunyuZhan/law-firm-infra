package com.lawfirm.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * 请求拦截器
 * 处理请求的统一拦截、验证和日志记录
 */
@Slf4j
@Component("apiRequestInterceptor")
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * 无需拦截的请求路径
     */
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
        "/static", "/webjars", "/error", "/favicon.ico", 
        "/actuator", "/api/test/hello"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求URI
        String uri = request.getRequestURI();
        
        // 检查是否为排除路径
        if (isExcludePath(uri)) {
            return true;
        }
        
        // 获取IP地址
        String ipAddress = getIpAddress(request);
        
        // 获取HTTP方法
        String method = request.getMethod();
        
        // 检查处理器类型
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            
            // 记录请求信息
            log.info("请求拦截 - IP: {}, URI: {}, Method: {}, Controller: {}, Action: {}", 
                    ipAddress, uri, method, controllerName, methodName);
        } else {
            log.info("请求拦截 - IP: {}, URI: {}, Method: {}", ipAddress, uri, method);
        }
        
        return true;
    }
    
    /**
     * 检查是否为排除路径
     */
    private boolean isExcludePath(String uri) {
        return EXCLUDE_PATHS.stream().anyMatch(uri::startsWith);
    }
    
    /**
     * 获取请求的真实IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
} 