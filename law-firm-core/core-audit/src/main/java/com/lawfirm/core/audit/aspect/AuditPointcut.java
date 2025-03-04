package com.lawfirm.core.audit.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 审计切点定义
 */
@Component
public class AuditPointcut {
    
    /**
     * 方法级审计切点
     */
    @Pointcut("@annotation(com.lawfirm.common.log.annotation.AuditLog)")
    public void auditLog() {}

    /**
     * 字段级审计切点
     */
    @Pointcut("@annotation(com.lawfirm.core.audit.annotation.AuditField)")
    public void auditField() {}

    /**
     * 模块级审计切点
     */
    @Pointcut("@within(com.lawfirm.core.audit.annotation.AuditModule)")
    public void auditModule() {}
} 