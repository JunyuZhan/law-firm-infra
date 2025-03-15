package com.lawfirm.document.service.strategy.security;

import com.lawfirm.model.document.entity.base.BaseDocument;

/**
 * 文档安全策略接口，定义文档安全相关的方法
 */
public interface SecurityStrategy {

    /**
     * 检查用户是否有权限访问指定文档
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    boolean hasDocumentPermission(String documentId, String userId, String action);

    /**
     * 检查用户是否有权限访问指定模板
     *
     * @param templateId 模板ID
     * @param userId 用户ID
     * @param action 操作类型（如：view, edit, delete等）
     * @return 是否有权限
     */
    boolean hasTemplatePermission(String templateId, String userId, String action);

    /**
     * 检查用户是否有文档管理权限
     *
     * @param userId 用户ID
     * @return 是否有权限
     */
    boolean hasDocumentManagementPermission(String userId);

    /**
     * 检查文档访问级别是否满足要求
     *
     * @param document 文档对象
     * @param userId 用户ID
     * @return 是否满足访问级别要求
     */
    boolean checkAccessLevel(BaseDocument document, String userId);

    /**
     * 获取安全策略名称
     *
     * @return 安全策略名称
     */
    String getStrategyName();
}
