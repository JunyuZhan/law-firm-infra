package com.lawfirm.cases.integration.auth;

import com.lawfirm.cases.service.CaseService;
import com.lawfirm.cases.service.TeamService;
import com.lawfirm.model.case_.constant.CaseConstants;
import com.lawfirm.model.case_.entity.Case;
import com.lawfirm.model.case_.entity.CaseTeamMember;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 案件权限提供者
 * 为权限系统提供案件相关的权限检查功能
 */
@Slf4j
@Component
@Lazy
@RequiredArgsConstructor
public class CasePermissionProvider {

    private final CaseService caseService;
    private final TeamService teamService;

    /**
     * 检查用户是否拥有对特定案件的操作权限
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @param operationType 操作类型
     * @return 是否有权限
     */
    public boolean hasCasePermission(Long userId, Long caseId, OperationTypeEnum operationType) {
        // 获取案件信息
        Case caseInfo = caseService.getById(caseId);
        if (caseInfo == null) {
            log.warn("案件不存在，caseId={}", caseId);
            return false;
        }

        // 案件负责人拥有所有权限
        if (userId.equals(caseInfo.getLeaderId())) {
            return true;
        }

        // 获取用户在案件中的角色
        List<CaseTeamMember> memberList = teamService.getCaseTeamMembers(caseId);
        
        // 检查用户是否在团队成员中
        List<CaseTeamMember> userMembers = memberList.stream()
                .filter(member -> member.getMemberId().equals(userId))
                .collect(Collectors.toList());
        
        if (userMembers.isEmpty()) {
            log.debug("用户不是案件团队成员，userId={}, caseId={}", userId, caseId);
            return false;
        }

        // 根据操作类型和用户角色判断权限
        for (CaseTeamMember member : userMembers) {
            String roleCode = member.getRoleCode();
            
            // 管理员有所有权限
            if (CaseConstants.ROLE_CASE_ADMIN.equals(roleCode)) {
                return true;
            }
            
            // 协办律师仅具有查看和编辑权限
            if (CaseConstants.ROLE_CASE_ASSISTANT.equals(roleCode)) {
                if (OperationTypeEnum.READ_ONLY.equals(operationType) || 
                    OperationTypeEnum.CREATE.equals(operationType)) {
                    return true;
                }
            }
            
            // 普通成员只有查看权限
            if (CaseConstants.ROLE_CASE_MEMBER.equals(roleCode)) {
                if (OperationTypeEnum.READ_ONLY.equals(operationType)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * 获取用户有权限访问的案件ID列表
     *
     * @param userId 用户ID
     * @param operationType 操作类型
     * @return 案件ID列表
     */
    public Set<Long> getUserAccessibleCaseIds(Long userId, OperationTypeEnum operationType) {
        // 获取用户参与的所有案件
        List<Case> userCases = caseService.getUserCases(userId);
        
        // 根据操作类型过滤有权限的案件
        return userCases.stream()
                .filter(caseInfo -> hasCasePermission(userId, caseInfo.getId(), operationType))
                .map(Case::getId)
                .collect(Collectors.toSet());
    }
} 