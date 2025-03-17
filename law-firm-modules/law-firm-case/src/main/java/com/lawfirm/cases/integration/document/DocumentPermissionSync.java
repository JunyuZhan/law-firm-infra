package com.lawfirm.cases.integration.document;

import com.lawfirm.cases.service.TeamService;
import com.lawfirm.model.case_.entity.CaseTeamMember;
import com.lawfirm.model.document.service.DocumentPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档权限同步
 * 负责将案件团队成员的权限同步到文档权限系统
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentPermissionSync {

    private final DocumentPermissionService documentPermissionService;
    private final TeamService teamService;

    /**
     * 同步案件团队成员权限到文档系统
     * 当案件团队成员变更时调用，确保文档权限与案件团队权限一致
     *
     * @param caseId 案件ID
     */
    @Async
    public void syncTeamPermissionsToDocuments(Long caseId) {
        if (caseId == null) {
            log.warn("案件ID为空，无法同步文档权限");
            return;
        }

        try {
            log.info("开始同步案件团队权限到文档系统，caseId={}", caseId);
            
            // 获取案件团队成员
            List<CaseTeamMember> teamMembers = teamService.getCaseTeamMembers(caseId);
            if (teamMembers.isEmpty()) {
                log.info("案件没有团队成员，不需要同步权限，caseId={}", caseId);
                return;
            }

            // 提取用户ID和角色信息
            List<DocumentPermissionService.UserPermission> userPermissions = teamMembers.stream()
                    .map(member -> {
                        String permission = mapRoleToPermission(member.getRoleCode());
                        return new DocumentPermissionService.UserPermission(member.getMemberId(), permission);
                    })
                    .collect(Collectors.toList());

            // 同步权限到文档系统
            documentPermissionService.syncBusinessDocumentsPermission("CASE", caseId, userPermissions);
            
            log.info("案件团队权限同步到文档系统完成，caseId={}", caseId);
        } catch (Exception e) {
            log.error("同步案件团队权限到文档系统异常，caseId={}", caseId, e);
        }
    }

    /**
     * 添加单个用户的文档权限
     *
     * @param caseId 案件ID
     * @param userId 用户ID
     * @param roleCode 角色编码
     */
    public void addUserDocumentPermission(Long caseId, Long userId, String roleCode) {
        if (caseId == null || userId == null || roleCode == null) {
            log.warn("参数无效，无法添加文档权限");
            return;
        }

        try {
            String permission = mapRoleToPermission(roleCode);
            documentPermissionService.addUserPermission("CASE", caseId, userId, permission);
            log.info("添加用户文档权限成功，caseId={}, userId={}, permission={}", caseId, userId, permission);
        } catch (Exception e) {
            log.error("添加用户文档权限异常，caseId={}, userId={}", caseId, userId, e);
        }
    }

    /**
     * 删除单个用户的文档权限
     *
     * @param caseId 案件ID
     * @param userId 用户ID
     */
    public void removeUserDocumentPermission(Long caseId, Long userId) {
        if (caseId == null || userId == null) {
            log.warn("参数无效，无法删除文档权限");
            return;
        }

        try {
            documentPermissionService.removeUserPermission("CASE", caseId, userId);
            log.info("删除用户文档权限成功，caseId={}, userId={}", caseId, userId);
        } catch (Exception e) {
            log.error("删除用户文档权限异常，caseId={}, userId={}", caseId, userId, e);
        }
    }

    /**
     * 将案件角色映射到文档权限
     *
     * @param roleCode 案件角色编码
     * @return 文档权限编码
     */
    private String mapRoleToPermission(String roleCode) {
        if (roleCode == null) {
            return "READ";
        }

        // 根据角色编码映射到对应的文档权限
        switch (roleCode) {
            case "CASE_ADMIN":
            case "CASE_LEADER":
                return "FULL"; // 管理员和负责人拥有完整权限
            case "CASE_ASSISTANT":
                return "EDIT"; // 协办律师拥有编辑权限
            case "CASE_MEMBER":
            default:
                return "READ"; // 团队成员拥有阅读权限
        }
    }
} 