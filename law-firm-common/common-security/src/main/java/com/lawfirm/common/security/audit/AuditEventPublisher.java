package com.lawfirm.common.security.audit;

/**
 * 审计事件发布者接口
 * 用于发布安全审计事件
 */
public interface AuditEventPublisher {
    
    /**
     * 发布认证事件
     * @param principal 认证主体
     * @param eventType 事件类型
     * @param result 认证结果
     */
    void publishAuthenticationEvent(Object principal, String eventType, boolean result);
    
    /**
     * 发布授权事件
     * @param principal 认证主体
     * @param permission 权限标识
     * @param result 授权结果
     */
    void publishAuthorizationEvent(Object principal, String permission, boolean result);
    
    /**
     * 发布操作事件
     * @param principal 认证主体
     * @param operation 操作类型
     * @param resource 资源标识
     */
    void publishOperationEvent(Object principal, String operation, String resource);
}