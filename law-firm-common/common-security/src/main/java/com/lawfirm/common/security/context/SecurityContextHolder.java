package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authorization.Authorization;

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
        return new DefaultSecurityContext();
    }

    /**
     * 检查当前用户是否拥有指定权限
     */
    public static boolean hasPermission(String permission) {
        SecurityContext context = getContext();
        Authorization authorization = context.getAuthorization();
        if (authorization == null) {
            return false;
        }
        return authorization.hasPermission(permission);
    }

    /**
     * 获取当前用户ID
     * @return 当前用户ID，未认证时可能返回null
     */
    public static Long getCurrentUserId() {
        return getContext().getCurrentUserId();
    }
} 