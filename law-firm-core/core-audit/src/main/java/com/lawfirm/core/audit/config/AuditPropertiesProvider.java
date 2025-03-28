package com.lawfirm.core.audit.config;

/**
 * 审计配置提供者接口
 * 业务层实现此接口以提供审计服务配置
 */
public interface AuditPropertiesProvider {
    
    /**
     * 获取审计服务配置
     * 
     * @return 审计配置属性
     */
    AuditProperties getAuditProperties();
} 