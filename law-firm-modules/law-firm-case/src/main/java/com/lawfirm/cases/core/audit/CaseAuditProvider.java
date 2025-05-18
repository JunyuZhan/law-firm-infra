package com.lawfirm.cases.core.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 案件审计提供者
 * <p>
 * 负责与core-audit模块集成，为审计模块提供案件相关的审计信息。
 * 主要提供案件创建、修改、状态变更等操作的审计记录。
 * </p>
 *
 * @author JunyuZhan
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseAuditProvider {
    
    // 注入审计服务
    @Autowired
    private AuditService auditService;
    
    /**
     * 记录案件创建审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param caseData 案件数据
     */
    public void auditCaseCreation(Long caseId, Long userId, Object caseData) {
        log.info("记录案件创建审计, 案件ID: {}, 用户: {}", caseId, userId);
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.CREATE)
                .module("CASE")
                .description("案件创建")
                .beforeData(null)
                .afterData(caseData != null ? caseData.toString() : null)
                .build();
        auditService.log(auditLog);
    }
    
    /**
     * 记录案件更新审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param oldData 旧数据
     * @param newData 新数据
     * @param changedFields 变更字段
     */
    public void auditCaseUpdate(Long caseId, Long userId, Object oldData, Object newData, Map<String, Object> changedFields) {
        log.info("记录案件更新审计, 案件ID: {}, 用户: {}, 变更字段数: {}", 
                caseId, userId, changedFields.size());
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.UPDATE)
                .module("CASE")
                .description("案件更新")
                .beforeData(oldData != null ? oldData.toString() : null)
                .afterData(newData != null ? newData.toString() : null)
                .changedFields(changedFields != null ? changedFields.toString() : null)
                .build();
        auditService.log(auditLog);
    }
    
    /**
     * 记录案件状态变更审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     * @param reason 变更原因
     */
    public void auditCaseStatusChange(Long caseId, Long userId, String oldStatus, String newStatus, String reason) {
        log.info("记录案件状态变更审计, 案件ID: {}, 用户: {}, 状态从 {} 变更到 {}", 
                caseId, userId, oldStatus, newStatus);
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.UPDATE)
                .module("CASE")
                .description("案件状态变更: " + reason)
                .beforeData(oldStatus)
                .afterData(newStatus)
                .build();
        auditService.log(auditLog);
    }
    
    /**
     * 记录团队成员变更审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param action 操作类型 (ADD/REMOVE/CHANGE_ROLE)
     * @param memberId 成员ID
     * @param role 角色
     */
    public void auditTeamMemberChange(Long caseId, Long userId, String action, Long memberId, String role) {
        log.info("记录团队成员变更审计, 案件ID: {}, 用户: {}, 操作: {}, 成员: {}", 
                caseId, userId, action, memberId);
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.UPDATE)
                .module("CASE")
                .description("团队成员" + action)
                .afterData("memberId=" + memberId + ",role=" + role)
                .build();
        auditService.log(auditLog);
    }
    
    /**
     * 记录案件文档操作审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param documentId 文档ID
     * @param action 操作类型 (UPLOAD/DELETE/DOWNLOAD/VIEW)
     */
    public void auditDocumentOperation(Long caseId, Long userId, Long documentId, String action) {
        log.info("记录文档操作审计, 案件ID: {}, 用户: {}, 文档: {}, 操作: {}", 
                caseId, userId, documentId, action);
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.valueOf(action.toUpperCase()))
                .module("CASE")
                .description("文档操作: " + action)
                .afterData("documentId=" + documentId)
                .build();
        auditService.log(auditLog);
    }
    
    /**
     * 记录费用变更审计
     *
     * @param caseId 案件ID
     * @param userId 操作用户ID
     * @param feeId 费用ID
     * @param action 操作类型 (ADD/MODIFY/DELETE)
     * @param amount 金额
     * @param reason 变更原因
     */
    public void auditFeeChange(Long caseId, Long userId, Long feeId, String action, Double amount, String reason) {
        log.info("记录费用变更审计, 案件ID: {}, 用户: {}, 费用: {}, 操作: {}, 金额: {}", 
                caseId, userId, feeId, action, amount);
        AuditLogDTO auditLog = AuditLogDTO.builder()
                .operatorId(userId)
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.valueOf(action.toUpperCase()))
                .module("CASE")
                .description("费用" + action + ": " + reason)
                .afterData("feeId=" + feeId + ",amount=" + amount)
                .build();
        auditService.log(auditLog);
    }
} 