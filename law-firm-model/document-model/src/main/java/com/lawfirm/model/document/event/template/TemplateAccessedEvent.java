package com.lawfirm.model.document.event.template;

import com.lawfirm.model.document.entity.template.TemplateDocument;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 模板访问事件
 */
@Getter
public class TemplateAccessedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final TemplateDocument template;

    public TemplateAccessedEvent(Object source, TemplateDocument template) {
        super(source);
        this.template = template;
    }
}