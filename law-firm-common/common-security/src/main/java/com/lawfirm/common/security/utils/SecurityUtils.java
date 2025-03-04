package com.lawfirm.common.security.utils;

import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.security.authentication.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 */
public class SecurityUtils {

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return Long.valueOf(((UserDetails) principal).getUsername());
        }
        return null;
    }

    /**
     * 获取当前用户名
     */
    public static String getUsername() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return String.valueOf(principal);
    }

    /**
     * 获取当前认证信息
     */
    public static org.springframework.security.core.Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断是否已认证
     */
    public static boolean isAuthenticated() {
        org.springframework.security.core.Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * 判断是否有指定权限
     */
    public static boolean hasPermission(String permission) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(permission));
    }

    /**
     * 判断是否有指定角色
     */
    public static boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
} 