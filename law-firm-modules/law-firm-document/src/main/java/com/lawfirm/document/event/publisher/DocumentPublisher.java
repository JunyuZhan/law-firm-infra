package com.lawfirm.document.event.publisher;

import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.entity.base.DocumentInfo;
import com.lawfirm.model.document.event.DocumentAccessedEvent;
import com.lawfirm.model.document.event.DocumentCreatedEvent;
import com.lawfirm.model.document.event.DocumentDeletedEvent;
import com.lawfirm.model.document.event.DocumentUpdatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 文档事件发布器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 发布文档创建事件
     */
    public void publishDocumentCreated(BaseDocument document) {
        log.debug("发布文档创建事件: {}", document.getId());
        eventPublisher.publishEvent(new DocumentCreatedEvent(this, document));
    }

    /**
     * 发布文档更新事件
     */
    public void publishDocumentUpdated(BaseDocument document) {
        log.debug("发布文档更新事件: {}", document.getId());
        eventPublisher.publishEvent(new DocumentUpdatedEvent(this, document));
    }

    /**
     * 发布文档访问事件
     */
    public void publishDocumentAccessed(DocumentInfo documentInfo) {
        log.debug("发布文档访问事件: {}", documentInfo.getId());
        eventPublisher.publishEvent(new DocumentAccessedEvent(this, documentInfo));
    }

    /**
     * 发布文档删除事件
     */
    public void publishDocumentDeleted(BaseDocument document) {
        log.debug("发布文档删除事件: {}", document.getId());
        eventPublisher.publishEvent(new DocumentDeletedEvent(this, document));
    }
}
