package com.lawfirm.model.cases.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件文档添加事件
 * 当新文档被添加到案件时触发
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseDocumentAddedEvent extends CaseEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 文档ID
     */
    private Long documentId;
    
    /**
     * 文档名称
     */
    private String documentName;
    
    /**
     * 文档类型
     */
    private String documentType;
    
    /**
     * 文档大小(字节)
     */
    private Long documentSize;
    
    /**
     * 文档描述
     */
    private String documentDescription;
    
    /**
     * 文档标签
     */
    private String[] documentTags;
    
    /**
     * 是否为机密文档
     */
    private boolean confidential;
    
    public CaseDocumentAddedEvent() {
        super();
    }
    
    public CaseDocumentAddedEvent(Long caseId, String caseNumber, Long documentId, String documentName) {
        super(caseId, caseNumber);
        this.documentId = documentId;
        this.documentName = documentName;
    }
    
    @Override
    public String getEventType() {
        return "CASE_DOCUMENT_ADDED";
    }
} 