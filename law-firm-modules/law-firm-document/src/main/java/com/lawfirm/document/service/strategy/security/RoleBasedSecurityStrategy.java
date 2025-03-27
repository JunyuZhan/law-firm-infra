package com.lawfirm.document.service.strategy.security;

import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 基于角色的文档安全策略实现
 */
@Slf4j
@Component
public class RoleBasedSecurityStrategy implements SecurityStrategy {

    private final Authorization authorization;

    /**
     * 构造函数，通过@Qualifier指定使用jdbc授权实现
     * @param authorization 授权接口实现
     */
    public RoleBasedSecurityStrategy(@Qualifier("jdbcAuthorization") Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public boolean hasDocumentPermission(String documentId, String userId, String action) {
        log.debug("检查用户[{}]是否有权限执行[{}]操作文档[{}]", userId, action, documentId);
        
        // 构建资源标识 - 实际应用中需要根据具体权限标识格式调整
        String permission = String.format("document:%s:%s", documentId, action);
        
        // 检查用户权限
        return authorization.hasPermission(permission);
    }

    @Override
    public boolean hasTemplatePermission(String templateId, String userId, String action) {
        log.debug("检查用户[{}]是否有权限执行[{}]操作模板[{}]", userId, action, templateId);
        
        // 构建资源标识 - 实际应用中需要根据具体权限标识格式调整
        String permission = String.format("template:%s:%s", templateId, action);
        
        // 检查用户权限
        return authorization.hasPermission(permission);
    }

    @Override
    public boolean hasDocumentManagementPermission(String userId) {
        log.debug("检查用户[{}]是否有文档管理权限", userId);
        
        // 检查用户是否有管理员角色
        // 需要使用正确的角色标识
        return authorization.hasRole("DOCUMENT_MANAGER") || 
               authorization.hasRole("ADMIN");
    }

    @Override
    public boolean checkAccessLevel(BaseDocument document, String userId) {
        log.debug("检查用户[{}]是否满足文档[{}]的访问级别要求", userId, document.getId());
        
        String accessLevel = document.getAccessLevel();
        
        // 如果是公开文档，任何人都可以访问
        if ("public".equalsIgnoreCase(accessLevel)) {
            return true;
        }
        
        // 如果是私有文档，只有创建者可以访问
        if ("private".equalsIgnoreCase(accessLevel)) {
            return userId.equals(document.getCreateBy());  // 使用已有的getCreateBy()方法
        }
        
        // 如果是受限文档，需要检查权限
        if ("restricted".equalsIgnoreCase(accessLevel)) {
            String permission = String.format("document:%s:view", document.getId());
            return authorization.hasPermission(permission);
        }
        
        // 默认不允许访问
        return false;
    }

    @Override
    public String getStrategyName() {
        return "role-based";
    }
} 