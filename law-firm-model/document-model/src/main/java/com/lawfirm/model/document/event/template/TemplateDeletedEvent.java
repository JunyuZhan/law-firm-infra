package com.lawfirm.model.document.event.template;

import com.lawfirm.model.document.entity.template.TemplateDocument;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 模板删除事件
 */
@Getter
public class TemplateDeletedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final TemplateDocument template;
    private final String reason;

    public TemplateDeletedEvent(Object source, TemplateDocument template, String reason) {
        super(source);
        this.template = template;
        this.reason = reason;
    }
}