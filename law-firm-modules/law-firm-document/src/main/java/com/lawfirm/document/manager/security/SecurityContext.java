package com.lawfirm.document.manager.security;

import com.lawfirm.common.security.authorization.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * 文档安全上下文，管理文档相关的安全配置和权限
 */
@Slf4j
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityContext {
    
    private final Authorization authorization;
    
    public SecurityContext(@Qualifier("jdbcAuthorization") Authorization authorization) {
        this.authorization = authorization;
    }
    
    /**
     * 检查用户是否有权限访问指定文档
     *
     * @param documentId 文档ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    public boolean hasDocumentPermission(String documentId, String action) {
        String permission = String.format("document:%s:%s", documentId, action);
        return authorization.hasPermission(permission);
    }
    
    /**
     * 检查用户是否有权限访问指定模板
     *
     * @param templateId 模板ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    public boolean hasTemplatePermission(String templateId, String action) {
        String permission = String.format("template:%s:%s", templateId, action);
        return authorization.hasPermission(permission);
    }
    
    /**
     * 检查用户是否有文档管理权限
     *
     * @return 是否有权限
     */
    public boolean hasDocumentManagementPermission() {
        return authorization.hasPermission("document:manage");
    }
    
    /**
     * 获取授权服务
     *
     * @return 授权服务
     */
    public Authorization getAuthorization() {
        return authorization;
    }
}
