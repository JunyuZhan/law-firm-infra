package com.lawfirm.document.event;

import com.lawfirm.model.document.enums.DocumentOperationEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 文档操作事件
 */
@Getter
public class DocumentOperationEvent extends ApplicationEvent {

    private final Long documentId;
    private final DocumentOperationEnum operationType;
    private final Long operatorId;
    private final String operatorName;

    public DocumentOperationEvent(Long documentId, DocumentOperationEnum operationType, Long operatorId, String operatorName) {
        super(documentId);
        this.documentId = documentId;
        this.operationType = operationType;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }
} 