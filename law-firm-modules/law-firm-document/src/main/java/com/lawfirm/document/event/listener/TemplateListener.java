package com.lawfirm.document.event.listener;

import com.lawfirm.model.document.event.template.TemplateAccessedEvent;
import com.lawfirm.model.document.event.template.TemplateCreatedEvent;
import com.lawfirm.model.document.event.template.TemplateDeletedEvent;
import com.lawfirm.model.document.event.template.TemplateUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 文档模板事件监听器
 */
@Slf4j
@Component
public class TemplateListener {

    /**
     * 监听模板创建事件
     */
    @EventListener
    public void onTemplateCreated(TemplateCreatedEvent event) {
        log.info("模板创建事件: {}", event.getTemplate().getId());
        // TODO: 实现模板创建后的业务逻辑
    }

    /**
     * 监听模板更新事件
     */
    @EventListener
    public void onTemplateUpdated(TemplateUpdatedEvent event) {
        log.info("模板更新事件: {}", event.getTemplate().getId());
        // TODO: 实现模板更新后的业务逻辑
    }

    /**
     * 监听模板访问事件
     */
    @EventListener
    public void onTemplateAccessed(TemplateAccessedEvent event) {
        log.info("模板访问事件: {}", event.getTemplate().getId());
        // TODO: 实现模板访问后的业务逻辑
    }

    /**
     * 监听模板删除事件
     */
    @EventListener
    public void onTemplateDeleted(TemplateDeletedEvent event) {
        log.info("模板删除事件: {}, 原因: {}", event.getTemplate().getId(), event.getReason());
        // TODO: 实现模板删除后的业务逻辑
    }
}