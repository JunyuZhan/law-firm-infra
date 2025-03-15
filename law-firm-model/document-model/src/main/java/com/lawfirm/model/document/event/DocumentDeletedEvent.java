package com.lawfirm.model.document.event;

import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 文档删除事件
 */
@Getter
public class DocumentDeletedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final BaseDocument document;

    public DocumentDeletedEvent(Object source, BaseDocument document) {
        super(source);
        this.document = document;
    }
} 