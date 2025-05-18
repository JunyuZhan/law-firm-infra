package com.lawfirm.document.event.listener;

import com.lawfirm.model.document.event.DocumentAccessedEvent;
import com.lawfirm.model.document.event.DocumentCreatedEvent;
import com.lawfirm.model.document.event.DocumentDeletedEvent;
import com.lawfirm.model.document.event.DocumentUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 文档事件监听器
 */
@Slf4j
@Component
public class DocumentListener {

    /**
     * 监听文档创建事件
     */
    @EventListener
    public void onDocumentCreated(DocumentCreatedEvent event) {
        log.info("文档创建事件: {}，可扩展为发送通知/索引等", event.getDocument().getId());
        // 例如：eventPublisher.publishEvent(new DocumentIndexEvent(event.getDocument()));
    }

    /**
     * 监听文档更新事件
     */
    @EventListener
    public void onDocumentUpdated(DocumentUpdatedEvent event) {
        log.info("文档更新事件: {}，可扩展为发送通知/索引等", event.getDocument().getId());
    }

    /**
     * 监听文档访问事件
     */
    @EventListener
    public void onDocumentAccessed(DocumentAccessedEvent event) {
        log.info("文档访问事件: {}，可扩展为记录访问日志/统计等", event.getDocumentInfo().getId());
    }

    /**
     * 监听文档删除事件
     */
    @EventListener
    public void onDocumentDeleted(DocumentDeletedEvent event) {
        log.info("文档删除事件: {}，可扩展为清理索引/发送通知等", event.getDocument().getId());
    }
}
