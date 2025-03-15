package com.lawfirm.document.manager.security;

import com.lawfirm.common.security.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 文档安全管理器，处理文档相关的安全操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityManager {

    private final SecurityContext securityContext;

    /**
     * 检查用户是否有权限访问指定文档
     *
     * @param documentId 文档ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    public boolean checkDocumentPermission(String documentId, String action) {
        return securityContext.hasDocumentPermission(documentId, action);
    }

    /**
     * 检查用户是否有权限访问指定模板
     *
     * @param templateId 模板ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    public boolean checkTemplatePermission(String templateId, String action) {
        return securityContext.hasTemplatePermission(templateId, action);
    }

    /**
     * 检查用户是否有文档管理权限
     *
     * @return 是否有权限
     */
    public boolean checkDocumentManagementPermission() {
        return securityContext.hasDocumentManagementPermission();
    }

    /**
     * 获取授权服务
     *
     * @return 授权服务
     */
    public Authorization getAuthorization() {
        return securityContext.getAuthorization();
    }
}
