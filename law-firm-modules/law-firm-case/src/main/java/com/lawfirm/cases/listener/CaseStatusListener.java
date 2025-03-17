package com.lawfirm.cases.listener;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.model.cases.event.CaseStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件状态变更监听器
 * <p>
 * 监听案件状态变更事件，处理状态变更后的通知和相关业务逻辑
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseStatusListener {

    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    
    /**
     * 监听案件状态变更事件
     *
     * @param event 案件状态变更事件
     */
    @EventListener
    public void onCaseStatusChanged(CaseStatusChangedEvent event) {
        log.info("监听到案件状态变更事件: 案件ID={}, 状态从{}变更到{}", 
                event.getCaseId(), event.getPreviousStatus(), event.getCurrentStatus());
        
        Long caseId = event.getCaseId();
        String caseNumber = event.getCaseNumber();
        
        try {
            // 1. 记录状态变更审计
            Long triggerId = 0L; // 实际应从事件中获取
            
            auditProvider.auditCaseStatusChange(
                    caseId,
                    triggerId,
                    event.getPreviousStatus(),
                    event.getCurrentStatus(),
                    "状态变更: " + event.getChangeReason()
            );
            
            // 2. 发送状态变更通知
            Map<String, Object> statusData = new HashMap<>();
            statusData.put("previousStatus", event.getPreviousStatus());
            statusData.put("currentStatus", event.getCurrentStatus());
            statusData.put("changeReason", event.getChangeReason());
            statusData.put("changeTime", event.getOccurTime());
            statusData.put("notifyClient", event.isNotifyClient());
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseNumber);
            messageData.put("eventType", "CASE_STATUS_CHANGED");
            messageData.put("statusData", statusData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            // 3. 处理特定状态变更的业务逻辑
            handleSpecificStatusChange(event);
            
            log.info("案件状态变更事件处理成功: 案件ID={}, 当前状态={}", 
                    caseId, event.getCurrentStatus());
        } catch (Exception e) {
            log.error("案件状态变更事件处理失败: 案件ID={}, 错误={}", caseId, e.getMessage(), e);
        }
    }
    
    /**
     * 处理特定状态变更的业务逻辑
     *
     * @param event 状态变更事件
     */
    private void handleSpecificStatusChange(CaseStatusChangedEvent event) {
        // 根据不同状态执行不同的业务逻辑
        String currentStatus = event.getCurrentStatus();
        Long caseId = event.getCaseId();
        
        switch (currentStatus) {
            case "1": // 草稿状态
                log.info("案件进入草稿状态: 案件ID={}", caseId);
                break;
            case "2": // 进行中状态
                log.info("案件进入进行中状态: 案件ID={}", caseId);
                // 可以触发其他业务操作，如创建默认任务等
                break;
            case "3": // 已完成状态
                log.info("案件已完成: 案件ID={}", caseId);
                // 可以触发结案相关操作
                break;
            case "4": // 已关闭状态
                log.info("案件已关闭: 案件ID={}", caseId);
                // 可以触发归档操作
                break;
            default:
                log.info("案件状态变更为{}: 案件ID={}", currentStatus, caseId);
                break;
        }
    }
} 