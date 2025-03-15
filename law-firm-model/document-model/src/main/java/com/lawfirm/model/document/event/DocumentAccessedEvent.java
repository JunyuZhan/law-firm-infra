package com.lawfirm.model.document.event;

import com.lawfirm.model.document.entity.base.DocumentInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 文档访问事件
 */
@Getter
public class DocumentAccessedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final DocumentInfo documentInfo;

    public DocumentAccessedEvent(Object source, DocumentInfo documentInfo) {
        super(source);
        this.documentInfo = documentInfo;
    }
} 