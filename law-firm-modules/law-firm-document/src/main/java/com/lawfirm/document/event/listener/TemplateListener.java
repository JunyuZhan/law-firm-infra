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
        log.info("模板创建事件: {}，可扩展为发送通知/索引等", event.getTemplate().getId());
        // 例如：eventPublisher.publishEvent(new TemplateIndexEvent(event.getTemplate()));
    }

    /**
     * 监听模板更新事件
     */
    @EventListener
    public void onTemplateUpdated(TemplateUpdatedEvent event) {
        log.info("模板更新事件: {}，可扩展为发送通知/索引等", event.getTemplate().getId());
    }

    /**
     * 监听模板访问事件
     */
    @EventListener
    public void onTemplateAccessed(TemplateAccessedEvent event) {
        log.info("模板访问事件: {}，可扩展为记录访问日志/统计等", event.getTemplate().getId());
    }

    /**
     * 监听模板删除事件
     */
    @EventListener
    public void onTemplateDeleted(TemplateDeletedEvent event) {
        log.info("模板删除事件: {}，原因: {}，可扩展为清理索引/发送通知等", event.getTemplate().getId(), event.getReason());
    }
}