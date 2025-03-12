package com.lawfirm.document.manager.security;

import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.common.security.audit.SecurityAudit;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.mapper.DocumentPermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文档安全管理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentSecurityManager {

    private final DocumentPermissionMapper documentPermissionMapper;
    private final PermissionService permissionService;
    private final SecurityAudit securityAudit;

    /**
     * 获取文档权限
     *
     * @param documentId 文档ID
     * @return 权限映射
     */
    @RequiresPermissions("document:view")
    public Map<String, Object> getPermissions(Long documentId) {
        List<DocumentPermission> permissions = documentPermissionMapper.findByDocumentId(documentId);

        return Map.of(
            "roles", permissionService.listPermissionsByRoleId(documentId),
            "users", permissions.stream()
                .filter(p -> "USER".equals(p.getSubjectType()))
                .map(p -> Map.of(
                    "userId", p.getSubjectId(),
                    "permission", p.getPermissionType()
                ))
                .toList()
        );
    }

    /**
     * 设置文档权限
     *
     * @param documentId 文档ID
     * @param permissions 权限配置
     */
    @RequiresPermissions("document:grant")
    public void setPermissions(Long documentId, Map<String, Object> permissions) {
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) permissions.get("roles");
        if (roles != null) {
            // TODO: 实现角色权限更新
        }

        @SuppressWarnings("unchecked")
        List<Map<String, String>> users = (List<Map<String, String>>) permissions.get("users");
        if (users != null) {
            documentPermissionMapper.deleteByDocumentId(documentId);

            users.forEach(user -> {
                DocumentPermission permission = new DocumentPermission();
                permission.setDocumentId(documentId);
                permission.setSubjectType("USER");
                permission.setSubjectId(Long.valueOf(user.get("userId")));
                permission.setPermissionType(user.get("permission"));
                permission.setPermissionSource("DIRECT");
                permission.setIsAllowed(true);
                permission.setIsEnabled(true);
                permission.setCreateBy(SecurityUtils.getUsername());
                documentPermissionMapper.insert(permission);
            });
        }

        securityAudit.logOperationEvent(
            SecurityUtils.getUsername(),
            "UPDATE_PERMISSION",
            "document:" + documentId
        );
    }

    /**
     * 获取文档操作日志
     *
     * @param documentId 文档ID
     * @return 操作日志列表
     */
    @RequiresPermissions("document:view")
    public List<Map<String, Object>> getAuditLogs(Long documentId) {
        // TODO: 实现审计日志查询
        return List.of();
    }
}
