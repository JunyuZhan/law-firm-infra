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
        log.info("文档创建事件: {}", event.getDocument().getId());
        // TODO: 实现文档创建后的业务逻辑
    }

    /**
     * 监听文档更新事件
     */
    @EventListener
    public void onDocumentUpdated(DocumentUpdatedEvent event) {
        log.info("文档更新事件: {}", event.getDocument().getId());
        // TODO: 实现文档更新后的业务逻辑
    }

    /**
     * 监听文档访问事件
     */
    @EventListener
    public void onDocumentAccessed(DocumentAccessedEvent event) {
        log.info("文档访问事件: {}", event.getDocumentInfo().getId());
        // TODO: 实现文档访问后的业务逻辑
    }

    /**
     * 监听文档删除事件
     */
    @EventListener
    public void onDocumentDeleted(DocumentDeletedEvent event) {
        log.info("文档删除事件: {}", event.getDocument().getId());
        // TODO: 实现文档删除后的业务逻辑
    }
}
