package com.lawfirm.cases.listener;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.process.definition.CaseProcessDefinition;
import com.lawfirm.model.cases.event.CaseStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件审批监听器
 * <p>
 * 监听案件状态变更事件，处理审批相关的业务逻辑
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseApprovalListener {
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    
    /**
     * 监听案件状态变更事件，处理审批相关逻辑
     *
     * @param event 案件状态变更事件
     */
    @Order(2)
    @EventListener
    public void onCaseStatusChanged(CaseStatusChangedEvent event) {
        log.info("审批监听器接收到案件状态变更事件: 案件ID={}, 状态从{}变更到{}", 
                event.getCaseId(), event.getPreviousStatus(), event.getCurrentStatus());
        
        // 只处理审批相关的状态变更
        if (!isApprovalStatusChange(event.getPreviousStatus(), event.getCurrentStatus())) {
            return;
        }
        
        Long caseId = event.getCaseId();
        String caseNumber = event.getCaseNumber();
        
        try {
            // 1. 记录审批状态变更审计
            Long triggerId = 0L; // 应从事件中获取，这里使用默认值
            
            auditProvider.auditCaseStatusChange(
                    caseId,
                    triggerId,
                    event.getPreviousStatus(),
                    event.getCurrentStatus(),
                    getApprovalAuditMessage(event.getCurrentStatus())
            );
            
            // 2. 发送审批状态变更消息
            Map<String, Object> approvalData = new HashMap<>();
            approvalData.put("previousStatus", event.getPreviousStatus());
            approvalData.put("currentStatus", event.getCurrentStatus());
            approvalData.put("changeReason", event.getChangeReason());
            approvalData.put("approvalNode", getApprovalNodeFromStatus(event.getCurrentStatus()));
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseNumber);
            messageData.put("eventType", "APPROVAL_STATUS_CHANGED");
            messageData.put("approvalData", approvalData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            log.info("案件审批状态变更处理成功: 案件ID={}, 当前状态={}", 
                    caseId, event.getCurrentStatus());
        } catch (Exception e) {
            log.error("案件审批状态变更处理失败: 案件ID={}, 错误={}", caseId, e.getMessage(), e);
        }
    }
    
    /**
     * 判断是否为审批相关的状态变更
     *
     * @param previousStatus 前一状态
     * @param currentStatus 当前状态
     * @return 是否为审批相关的状态变更
     */
    private boolean isApprovalStatusChange(String previousStatus, String currentStatus) {
        // 审批相关状态的列表
        String[] approvalStatuses = {
                CaseProcessDefinition.STATUS_SUBMITTED,
                CaseProcessDefinition.STATUS_UNDER_REVIEW,
                CaseProcessDefinition.STATUS_APPROVED,
                CaseProcessDefinition.STATUS_REJECTED
        };
        
        // 检查当前状态是否为审批相关状态
        boolean isCurrentApprovalStatus = false;
        for (String status : approvalStatuses) {
            if (status.equals(currentStatus)) {
                isCurrentApprovalStatus = true;
                break;
            }
        }
        
        return isCurrentApprovalStatus;
    }
    
    /**
     * 获取审批状态对应的审计消息
     *
     * @param status 状态
     * @return 审计消息
     */
    private String getApprovalAuditMessage(String status) {
        switch (status) {
            case CaseProcessDefinition.STATUS_SUBMITTED:
                return "案件已提交审批";
            case CaseProcessDefinition.STATUS_UNDER_REVIEW:
                return "案件正在审批中";
            case CaseProcessDefinition.STATUS_APPROVED:
                return "案件已通过审批";
            case CaseProcessDefinition.STATUS_REJECTED:
                return "案件审批被拒绝";
            default:
                return "案件状态已更新: " + status;
        }
    }
    
    /**
     * 获取状态对应的审批节点
     *
     * @param status 状态
     * @return 审批节点
     */
    private String getApprovalNodeFromStatus(String status) {
        switch (status) {
            case CaseProcessDefinition.STATUS_SUBMITTED:
                return CaseProcessDefinition.NODE_SUBMIT;
            case CaseProcessDefinition.STATUS_UNDER_REVIEW:
                return CaseProcessDefinition.NODE_REVIEW;
            case CaseProcessDefinition.STATUS_APPROVED:
                return "approve";
            case CaseProcessDefinition.STATUS_REJECTED:
                return "reject";
            default:
                return "";
        }
    }
} 