package com.lawfirm.cases.process.handler;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.process.definition.CaseApprovalDefinition;
import com.lawfirm.cases.process.definition.CaseProcessDefinition;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 案件审批流程处理器
 * <p>
 * 处理案件审批流程，包括审批流转、状态更新、通知发送等
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseApprovalHandler {
    
    private final CaseMapper caseMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    
    /**
     * 处理案件审批
     *
     * @param caseId 案件ID
     * @param approvalType 审批类型
     * @param approved 是否批准
     * @param role 审批人角色
     * @param userId 审批人ID
     * @param comment 审批意见
     * @return 是否处理成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean handleApproval(Long caseId, String approvalType, boolean approved, 
                                 String role, Long userId, String comment) {
        log.info("处理案件审批: 案件ID={}, 审批类型={}, 审批结果={}, 审批人角色={}", 
                caseId, approvalType, approved ? "批准" : "拒绝", role);
        
        try {
            // 1. 获取案件信息
            Case caseEntity = caseMapper.selectById(caseId);
            if (caseEntity == null) {
                log.error("案件不存在: {}", caseId);
                return false;
            }
            
            // 2. 验证审批权限
            if (!CaseApprovalDefinition.hasApprovalAuthority(approvalType, role)) {
                log.error("无审批权限: 案件ID={}, 角色={}, 审批类型={}", caseId, role, approvalType);
                return false;
            }
            
            // 3. 处理审批结果
            LocalDateTime now = LocalDateTime.now();
            boolean isFinalApprover = CaseApprovalDefinition.isFinalApprover(approvalType, role);
            String approvalStatus;
            String caseStatus = String.valueOf(caseEntity.getCaseStatus());
            String processEvent;
            
            if (approved) {
                // 如果批准
                approvalStatus = CaseApprovalDefinition.STATUS_APPROVED;
                
                if (isFinalApprover) {
                    // 最终审批人批准，更新案件状态为已批准
                    processEvent = CaseProcessDefinition.EVENT_APPROVED;
                    String nextStatus = CaseProcessDefinition.getNextStatus(
                            CaseProcessDefinition.STATUS_UNDER_REVIEW, processEvent);
                    
                    if (nextStatus != null) {
                        caseEntity.setCaseStatus(Integer.parseInt(nextStatus));
                        caseStatus = nextStatus;
                    }
                } else {
                    // 非最终审批人批准，流转到下一审批人
                    processEvent = CaseProcessDefinition.EVENT_REVIEWED;
                }
            } else {
                // 如果拒绝，更新状态为已拒绝
                approvalStatus = CaseApprovalDefinition.STATUS_REJECTED;
                processEvent = CaseProcessDefinition.EVENT_REJECTED;
                
                String nextStatus = CaseProcessDefinition.getNextStatus(
                        CaseProcessDefinition.STATUS_UNDER_REVIEW, processEvent);
                
                if (nextStatus != null) {
                    caseEntity.setCaseStatus(Integer.parseInt(nextStatus));
                    caseStatus = nextStatus;
                }
            }
            
            // 更新案件信息
            caseEntity.setUpdateTime(now);
            caseEntity.setUpdateBy(String.valueOf(userId));
            caseEntity.setLastOperationTime(now);
            caseEntity.setLastOperatorId(userId);
            
            caseMapper.updateById(caseEntity);
            
            // 4. 记录审计
            auditProvider.auditCaseStatusChange(
                    caseId,
                    userId,
                    String.valueOf(caseEntity.getCaseStatus()),
                    caseStatus,
                    "审批" + (approved ? "通过" : "拒绝") + ": " + comment
            );
            
            // 5. 发送审批结果通知
            Map<String, Object> approvalData = new HashMap<>();
            approvalData.put("approvalType", approvalType);
            approvalData.put("status", approvalStatus);
            approvalData.put("role", role);
            approvalData.put("userId", userId);
            approvalData.put("approvalTime", now);
            approvalData.put("comment", comment);
            approvalData.put("isFinal", isFinalApprover);
            
            if (approved && !isFinalApprover) {
                // 如果批准但不是最终审批人，添加下一个审批人角色
                String nextRole = CaseApprovalDefinition.getNextApprovalRole(approvalType, role);
                if (nextRole != null) {
                    approvalData.put("nextRole", nextRole);
                }
            }
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseEntity.getCaseNumber());
            messageData.put("eventType", "APPROVAL_UPDATED");
            messageData.put("approvalData", approvalData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), userId);
            
            log.info("案件审批处理成功: 案件ID={}, 审批结果={}, 审批状态={}", 
                    caseId, approved ? "批准" : "拒绝", approvalStatus);
            return true;
        } catch (Exception e) {
            log.error("案件审批处理失败: {}", e.getMessage(), e);
            throw new RuntimeException("处理案件审批失败", e);
        }
    }
}
