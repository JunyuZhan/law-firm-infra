package com.lawfirm.model.organization.service;

import java.util.List;

/**
 * 组织与认证模块的桥接接口
 * 用于处理组织与用户权限之间的关联
 */
public interface OrganizationAuthBridge {

    /**
     * 检查用户是否在指定组织中
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @return 是否在组织中
     */
    boolean isUserInOrganization(Long userId, Long organizationId);
    
    /**
     * 获取用户可管理的组织ID列表
     *
     * @param userId 用户ID
     * @return 可管理的组织ID列表
     */
    List<Long> getUserManagementOrganizationIds(Long userId);
    
    /**
     * 获取用户可访问的组织ID列表
     *
     * @param userId 用户ID
     * @return 可访问的组织ID列表
     */
    List<Long> getUserAccessibleOrganizationIds(Long userId);
    
    /**
     * 检查用户是否对指定组织有特定权限
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @param permission 权限代码
     * @return 是否有权限
     */
    boolean hasOrganizationPermission(Long userId, Long organizationId, String permission);
} 