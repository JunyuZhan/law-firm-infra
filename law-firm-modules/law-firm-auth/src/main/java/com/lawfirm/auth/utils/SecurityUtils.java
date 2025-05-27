package com.lawfirm.auth.utils;

import com.lawfirm.auth.security.details.SecurityUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 安全工具类
 */
public class SecurityUtils {
    
    /**
     * 获取当前登录用户名
     * 
     * @return 用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        
        return principal.toString();
    }
    
    /**
     * 获取当前登录用户ID
     * 
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUserDetails) {
            return ((SecurityUserDetails) principal).getUserId();
        }
        
        // 如果不是SecurityUserDetails，尝试从用户名解析（兼容处理）
        String username = getCurrentUsername();
        if (username != null) {
            try {
                return Long.parseLong(username);
            } catch (NumberFormatException e) {
                // 如果用户名不是数字，返回默认值
                return 1L;
            }
        }
        
        return null;
    }
    
    /**
     * 从请求中获取JWT令牌
     * 
     * @param request HTTP请求
     * @return JWT令牌
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    /**
     * 判断当前用户是否已认证
     * 
     * @return 是否已认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
    
    /**
     * 获取用户ID（兼容方法）
     * 
     * @return 用户ID
     */
    public static Long getUserId() {
        return getCurrentUserId();
    }
    
    /**
     * 获取用户名（兼容方法）
     * 
     * @return 用户名
     */
    public static String getUsername() {
        return getCurrentUsername();
    }
    
    /**
     * 获取客户端IP地址
     * 
     * @param request HTTP请求
     * @return IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        
        return ip;
    }
}

