package com.lawfirm.model.cases.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件创建事件
 * 当新案件被创建时触发
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseCreatedEvent extends CaseEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 案件类型
     */
    private String caseType;
    
    /**
     * 案件标题
     */
    private String caseTitle;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 负责律师ID
     */
    private Long responsibleLawyerId;
    
    /**
     * 负责律师姓名
     */
    private String responsibleLawyerName;
    
    public CaseCreatedEvent() {
        super();
    }
    
    public CaseCreatedEvent(Long caseId, String caseNumber) {
        super(caseId, caseNumber);
    }
    
    @Override
    public String getEventType() {
        return "CASE_CREATED";
    }
} 