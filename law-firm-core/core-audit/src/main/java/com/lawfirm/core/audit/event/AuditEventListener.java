package com.lawfirm.core.audit.event;

import com.lawfirm.model.log.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 审计事件监听器
 */
@Component
@RequiredArgsConstructor
public class AuditEventListener {

    private final AuditService auditService;

    @Async("auditAsyncExecutor")
    @Order
    @EventListener(AuditEvent.class)
    public void onAuditEvent(AuditEvent event) {
        auditService.log(event.getAuditLog());
    }
} 