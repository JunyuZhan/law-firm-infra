package com.lawfirm.core.audit.event;

import com.lawfirm.model.log.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 审计事件监听器
 * 只在审计功能启用时加载
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuditEventListener {

    private final AuditService auditService;

    @Async("auditAsyncExecutor")
    @Order
    @EventListener(AuditEvent.class)
    public void onAuditEvent(AuditEvent event) {
        auditService.log(event.getAuditLog());
    }
} 