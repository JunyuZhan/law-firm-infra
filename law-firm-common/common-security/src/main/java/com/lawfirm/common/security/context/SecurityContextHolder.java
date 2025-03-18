package com.lawfirm.common.security.context;

import com.lawfirm.common.security.core.SecurityContext;
import com.lawfirm.common.security.core.UserDetails;

/**
 * 安全上下文持有者，用于存储和获取当前用户的安全信息
 */
public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    /**
     * 获取当前安全上下文
     */
    public static SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    /**
     * 设置安全上下文
     */
    public static void setContext(SecurityContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Only non-null SecurityContext instances are permitted");
        }
        contextHolder.set(context);
    }

    /**
     * 清除安全上下文
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * 创建空的安全上下文
     */
    private static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

    /**
     * 检查当前用户是否拥有指定权限
     */
    public static boolean hasPermission(String permission) {
        SecurityContext context = getContext();
        UserDetails userDetails = context.getUserDetails();
        if (userDetails == null || userDetails.getPermissions() == null) {
            return false;
        }
        return userDetails.getPermissions().contains(permission);
    }

    /**
     * 获取当前用户ID
     * @return 当前用户ID，未认证时可能返回null
     */
    public static Long getCurrentUserId() {
        return getContext().getCurrentUserId();
    }
} 