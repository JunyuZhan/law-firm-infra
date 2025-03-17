package com.lawfirm.model.cases.event;

/**
 * 案件事件处理器接口
 * 定义处理各类案件事件的方法
 */
public interface CaseEventHandler {
    
    /**
     * 处理案件创建事件
     * 
     * @param event 案件创建事件
     */
    void handleCaseCreated(CaseCreatedEvent event);
    
    /**
     * 处理案件状态变更事件
     * 
     * @param event 案件状态变更事件
     */
    void handleCaseStatusChanged(CaseStatusChangedEvent event);
    
    /**
     * 处理案件文档添加事件
     * 
     * @param event 案件文档添加事件
     */
    void handleCaseDocumentAdded(CaseDocumentAddedEvent event);
    
    /**
     * 通用事件处理方法
     * 
     * @param event 案件事件
     */
    void handleEvent(CaseEvent event);
} 