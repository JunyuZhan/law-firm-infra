package com.lawfirm.common.security.context;

import org.springframework.stereotype.Component;

/**
 * 基于ThreadLocal的安全上下文持有者
 * 提供线程级别的安全上下文存储和管理
 */
@Component
public class ThreadLocalSecurityContextHolder {
    
    private final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();
    
    /**
     * 获取当前安全上下文
     * 如果不存在则创建新的空上下文
     */
    public SecurityContext getContext() {
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
    public void setContext(SecurityContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Only non-null SecurityContext instances are permitted");
        }
        contextHolder.set(context);
    }
    
    /**
     * 清除安全上下文
     */
    public void clearContext() {
        contextHolder.remove();
    }
    
    /**
     * 创建空的安全上下文
     */
    private SecurityContext createEmptyContext() {
        return new DefaultSecurityContext();
    }
} 