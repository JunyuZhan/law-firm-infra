package com.lawfirm.document.event.publisher;

import com.lawfirm.model.document.event.template.TemplateCreatedEvent;
import com.lawfirm.model.document.event.template.TemplateUpdatedEvent;
import com.lawfirm.model.document.event.template.TemplateAccessedEvent;
import com.lawfirm.model.document.event.template.TemplateDeletedEvent;

import com.lawfirm.model.document.entity.template.TemplateDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;



/**
 * 文档模板事件发布器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TemplatePublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 发布模板创建事件
     */
    public void publishTemplateCreated(TemplateDocument template) {
        log.debug("发布模板创建事件: {}", template.getId());
        eventPublisher.publishEvent(new TemplateCreatedEvent(this, template));
    }

    /**
     * 发布模板更新事件
     */
    public void publishTemplateUpdated(TemplateDocument template) {
        log.debug("发布模板更新事件: {}", template.getId());
        eventPublisher.publishEvent(new TemplateUpdatedEvent(this, template));
    }

    /**
     * 发布模板访问事件
     */
    public void publishTemplateAccessed(TemplateDocument template) {
        log.debug("发布模板访问事件: {}", template.getId());
        eventPublisher.publishEvent(new TemplateAccessedEvent(this, template));
    }

    /**
     * 发布模板删除事件
     */
    public void publishTemplateDeleted(TemplateDocument template) {
        log.debug("发布模板删除事件: {}", template.getId());
        eventPublisher.publishEvent(new TemplateDeletedEvent(this, template, "用户手动删除"));
    }
}

