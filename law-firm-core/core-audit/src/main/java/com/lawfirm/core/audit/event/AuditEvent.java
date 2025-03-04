package com.lawfirm.core.audit.event;

import com.lawfirm.model.log.dto.AuditLogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 审计事件
 */
@Getter
public class AuditEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    private final AuditLogDTO auditLog;
    
    public AuditEvent(Object source, AuditLogDTO auditLog) {
        super(source);
        this.auditLog = auditLog;
    }
} 