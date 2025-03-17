package com.lawfirm.cases.process.listener;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.process.definition.CaseProcessDefinition;
import com.lawfirm.model.cases.event.CaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件启动流程监听器
 * <p>
 * 监听案件创建事件，触发流程启动逻辑
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseStartListener {

    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    
    /**
     * 监听案件创建事件
     *
     * @param event 案件创建事件
     */
    @EventListener
    public void onCaseCreated(CaseCreatedEvent event) {
        log.info("案件创建事件触发流程启动: 案件ID={}, 案件编号={}", 
                event.getCaseId(), event.getCaseNumber());
        
        Long caseId = event.getCaseId();
        String caseNumber = event.getCaseNumber();
        
        try {
            // 1. 记录流程启动审计
            Long triggerId = 0L; // 应从事件中获取，这里使用默认值
            
            auditProvider.auditCaseStatusChange(
                    caseId,
                    triggerId,
                    null, // 初始无状态
                    CaseProcessDefinition.STATUS_DRAFT, // 状态为草稿
                    "流程启动：案件创建"
            );
            
            // 2. 发送流程启动消息
            Map<String, Object> processData = new HashMap<>();
            processData.put("processStatus", CaseProcessDefinition.STATUS_DRAFT);
            processData.put("currentNode", CaseProcessDefinition.NODE_CREATE);
            processData.put("caseType", event.getCaseType());
            processData.put("clientName", event.getClientName());
            processData.put("responsibleLawyer", event.getResponsibleLawyerName());
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseNumber);
            messageData.put("eventType", "PROCESS_STARTED");
            messageData.put("processData", processData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            log.info("案件流程启动成功: 案件ID={}, 流程状态={}", 
                    caseId, CaseProcessDefinition.STATUS_DRAFT);
        } catch (Exception e) {
            log.error("案件流程启动失败: 案件ID={}, 错误={}", caseId, e.getMessage(), e);
        }
    }
}
