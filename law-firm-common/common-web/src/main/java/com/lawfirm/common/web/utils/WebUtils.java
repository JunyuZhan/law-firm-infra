package com.lawfirm.common.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Web工具类
 */
public class WebUtils {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前线程中不存在Request上下文");
        }
        return ((ServletRequestAttributes) attributes).getRequest();
    }

    /**
     * 获取客户端IP地址
     */
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
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
        return ip;
    }

    /**
     * 获取请求URL
     */
    public static String getRequestUrl() {
        HttpServletRequest request = getRequest();
        return request.getRequestURL().toString();
    }

    /**
     * 获取请求URI
     */
    public static String getRequestUri() {
        HttpServletRequest request = getRequest();
        return request.getRequestURI();
    }

    /**
     * 获取请求方法
     */
    public static String getRequestMethod() {
        HttpServletRequest request = getRequest();
        return request.getMethod();
    }

    /**
     * 获取请求参数
     */
    public static String getRequestParam(String name) {
        HttpServletRequest request = getRequest();
        return request.getParameter(name);
    }

    /**
     * 获取请求头
     */
    public static String getRequestHeader(String name) {
        HttpServletRequest request = getRequest();
        return request.getHeader(name);
    }
} 