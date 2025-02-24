package com.lawfirm.common.security.audit;

/**
 * 安全审计基础接口
 */
public interface SecurityAudit {
    
    /**
     * 记录认证事件
     * @param principal 认证主体
     * @param eventType 事件类型
     * @param result 认证结果
     */
    void logAuthenticationEvent(Object principal, String eventType, boolean result);
    
    /**
     * 记录授权事件
     * @param principal 认证主体
     * @param permission 权限标识
     * @param result 授权结果
     */
    void logAuthorizationEvent(Object principal, String permission, boolean result);
    
    /**
     * 记录操作事件
     * @param principal 认证主体
     * @param operation 操作类型
     * @param resource 资源标识
     */
    void logOperationEvent(Object principal, String operation, String resource);
} 