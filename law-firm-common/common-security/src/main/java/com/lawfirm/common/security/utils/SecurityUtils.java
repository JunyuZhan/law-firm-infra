package com.lawfirm.common.security.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import com.lawfirm.common.core.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 */
public class SecurityUtils {
    
    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        try {
            return StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            throw new BusinessException("获取用户ID异常");
        }
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        try {
            Object loginId = StpUtil.getLoginId();
            return loginId != null ? loginId.toString() : null;
        } catch (Exception e) {
            throw new BusinessException("获取用户名异常");
        }
    }

    /**
     * 获取用户key
     */
    public static String getUserKey() {
        try {
            Object userKey = SaHolder.getStorage().get("userKey");
            return userKey != null ? userKey.toString() : null;
        } catch (Exception e) {
            throw new BusinessException("获取用户Key异常");
        }
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        try {
            Object tenantId = SaHolder.getStorage().get("tenantId");
            return tenantId != null ? Long.valueOf(tenantId.toString()) : null;
        } catch (Exception e) {
            throw new BusinessException("获取租户ID异常");
        }
    }

    /**
     * 是否为管理员
     */
    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

    /**
     * 是否为管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 生成BCryptPasswordEncoder密码
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
} 