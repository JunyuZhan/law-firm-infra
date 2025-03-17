package com.lawfirm.cases.process.handler;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.process.definition.CaseApprovalDefinition;
import com.lawfirm.cases.process.definition.CaseProcessDefinition;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
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
 * 案件创建流程处理器
 * <p>
 * 处理案件创建流程，包括保存案件数据、分配案件编号、启动审批流程等
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseCreateHandler {

    private final CaseMapper caseMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    
    /**
     * 处理案件创建流程
     *
     * @param createDTO 案件创建DTO
     * @param currentUserId 当前用户ID
     * @return 案件ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long handleCaseCreate(CaseCreateDTO createDTO, Long currentUserId) {
        log.info("处理案件创建流程: 案件名称={}, 创建人={}", createDTO.getCaseName(), currentUserId);
        
        try {
            // 1. 创建案件实体
            Case caseEntity = new Case();
            
            // 设置基本信息
            caseEntity.setCaseName(createDTO.getCaseName());
            caseEntity.setCaseDescription(createDTO.getCaseDescription());
            caseEntity.setCaseType(createDTO.getCaseType() != null ? 
                    createDTO.getCaseType().getValue() : null);
            caseEntity.setCaseStatus(1); // 初始状态 - 草稿
            caseEntity.setClientId(createDTO.getClientId());
            caseEntity.setClientName(createDTO.getClientName());
            caseEntity.setLawyerId(createDTO.getLawyerId());
            caseEntity.setLawyerName(createDTO.getLawyerName());
            
            // 设置时间和审计字段
            LocalDateTime now = LocalDateTime.now();
            caseEntity.setCreateTime(now);
            caseEntity.setUpdateTime(now);
            caseEntity.setCreateBy(String.valueOf(currentUserId));
            caseEntity.setUpdateBy(String.valueOf(currentUserId));
            caseEntity.setDeleted(0);
            
            // 生成案件编号 (实际应该有更复杂的生成逻辑)
            String caseNumber = "CASE-" + System.currentTimeMillis();
            caseEntity.setCaseNumber(caseNumber);
            
            // 2. 保存案件
            caseMapper.insert(caseEntity);
            Long caseId = caseEntity.getId();
            
            // 3. 记录审计
            auditProvider.auditCaseCreation(caseId, currentUserId, caseEntity);
            
            // 4. 启动审批流程 (如果需要审批)
            boolean needApproval = true; // 实际应根据业务规则判断
            
            if (needApproval) {
                // 启动案件创建审批流程
                Map<String, Object> approvalData = new HashMap<>();
                approvalData.put("approvalType", CaseApprovalDefinition.TYPE_CASE_CREATE);
                approvalData.put("status", CaseApprovalDefinition.STATUS_PENDING);
                approvalData.put("currentRole", CaseApprovalDefinition.ROLE_DEPARTMENT_MANAGER);
                approvalData.put("submitterId", currentUserId);
                approvalData.put("submitTime", now);
                
                // 发送审批通知
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("caseId", caseId);
                messageData.put("caseNumber", caseNumber);
                messageData.put("eventType", "APPROVAL_STARTED");
                messageData.put("approvalData", approvalData);
                
                messageManager.sendCaseTeamChangeMessage(caseId, 
                        java.util.List.of(messageData), currentUserId);
                
                log.info("案件创建审批流程已启动: 案件ID={}, 审批类型={}", 
                        caseId, CaseApprovalDefinition.TYPE_CASE_CREATE);
            }
            
            log.info("案件创建流程处理成功: 案件ID={}, 案件编号={}", caseId, caseNumber);
            return caseId;
        } catch (Exception e) {
            log.error("案件创建流程处理失败: {}", e.getMessage(), e);
            throw new RuntimeException("处理案件创建流程失败", e);
        }
    }
}
