package com.lawfirm.cases.event;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.process.definition.CaseProcessDefinition;
import com.lawfirm.model.cases.event.CaseCreatedEvent;
import com.lawfirm.model.cases.event.CaseDocumentAddedEvent;
import com.lawfirm.model.cases.event.CaseStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件事件处理器
 * <p>
 * 集中处理案件相关的事件，进行审计记录、消息通知等操作
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseEventHandler {
    
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final ApplicationEventPublisher eventPublisher;
    
    /**
     * 处理案件创建事件
     *
     * @param event 案件创建事件
     */
    @Order(1)
    @EventListener
    public void handleCaseCreatedEvent(CaseCreatedEvent event) {
        log.info("处理案件创建事件: 案件ID={}, 案件编号={}", 
                event.getCaseId(), event.getCaseNumber());
        
        try {
            // 1. 记录审计
            Long triggerId = 0L; // 实际应从事件中获取
            
            auditProvider.auditCaseCreation(
                    event.getCaseId(),
                    triggerId,
                    Map.of(
                        "caseType", event.getCaseType(),
                        "caseTitle", event.getCaseTitle(),
                        "clientName", event.getClientName(),
                        "responsibleLawyer", event.getResponsibleLawyerName()
                    )
            );
            
            // 2. 发送消息通知
            Map<String, Object> messageData = Map.of(
                "eventType", "CASE_CREATED",
                "caseId", event.getCaseId(),
                "caseNumber", event.getCaseNumber(),
                "caseTitle", event.getCaseTitle(),
                "clientName", event.getClientName(),
                "responsibleLawyer", event.getResponsibleLawyerName()
            );
            
            messageManager.sendCaseTeamChangeMessage(
                    event.getCaseId(), 
                    java.util.List.of(messageData),
                    triggerId
            );
            
            log.info("案件创建事件处理成功: 案件ID={}", event.getCaseId());
        } catch (Exception e) {
            log.error("案件创建事件处理失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理案件状态变更事件
     *
     * @param event 案件状态变更事件
     */
    @Order(1)
    @EventListener
    public void handleCaseStatusChangedEvent(CaseStatusChangedEvent event) {
        log.info("处理案件状态变更事件: 案件ID={}, 状态从{}变更到{}", 
                event.getCaseId(), event.getPreviousStatus(), event.getCurrentStatus());
        
        // 仅处理完成状态的事件
        if (!CaseProcessDefinition.STATUS_COMPLETED.equals(event.getCurrentStatus()) && 
            !CaseProcessDefinition.STATUS_CLOSED.equals(event.getCurrentStatus())) {
            return;
        }
        
        Long caseId = event.getCaseId();
        String caseNumber = event.getCaseNumber();
        
        try {
            // 1. 记录流程完成审计
            Long triggerId = 0L; // 应从事件中获取，这里使用默认值
            
            auditProvider.auditCaseStatusChange(
                    caseId,
                    triggerId,
                    event.getPreviousStatus(),
                    event.getCurrentStatus(),
                    "流程完成：案件" + 
                    (CaseProcessDefinition.STATUS_COMPLETED.equals(event.getCurrentStatus()) ? 
                            "已完成" : "已关闭")
            );
            
            // 2. 发送流程完成消息
            Map<String, Object> processData = new HashMap<>();
            processData.put("processStatus", event.getCurrentStatus());
            processData.put("currentNode", 
                    CaseProcessDefinition.STATUS_COMPLETED.equals(event.getCurrentStatus()) ? 
                    CaseProcessDefinition.NODE_COMPLETE : CaseProcessDefinition.NODE_CLOSE);
            processData.put("changeReason", event.getChangeReason());
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseNumber);
            messageData.put("eventType", 
                    CaseProcessDefinition.STATUS_COMPLETED.equals(event.getCurrentStatus()) ? 
                    "PROCESS_COMPLETED" : "PROCESS_CLOSED");
            messageData.put("processData", processData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            log.info("案件流程完成处理成功: 案件ID={}, 流程状态={}", 
                    caseId, event.getCurrentStatus());
        } catch (Exception e) {
            log.error("案件流程完成处理失败: 案件ID={}, 错误={}", caseId, e.getMessage(), e);
        }
    }
}
