package com.lawfirm.model.cases.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件状态变更事件
 * 当案件状态发生变化时触发
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseStatusChangedEvent extends CaseEvent {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 原状态
     */
    private String previousStatus;
    
    /**
     * 新状态
     */
    private String currentStatus;
    
    /**
     * 变更原因
     */
    private String changeReason;
    
    /**
     * 是否需要通知客户
     */
    private boolean notifyClient;
    
    /**
     * 是否需要执行后续工作流
     */
    private boolean triggerWorkflow;
    
    public CaseStatusChangedEvent() {
        super();
    }
    
    public CaseStatusChangedEvent(Long caseId, String caseNumber, 
                                 String previousStatus, String currentStatus) {
        super(caseId, caseNumber);
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
    }
    
    @Override
    public String getEventType() {
        return "CASE_STATUS_CHANGED";
    }
} 