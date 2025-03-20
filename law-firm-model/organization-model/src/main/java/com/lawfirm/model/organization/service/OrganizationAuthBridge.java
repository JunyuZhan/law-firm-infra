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
     * 检查用户是否有组织权限
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @param permission 权限代码
     * @return 是否有权限
     */
    boolean checkOrganizationPermission(Long userId, Long organizationId, String permission);

    /**
     * 获取用户有权限的组织列表
     *
     * @param userId 用户ID
     * @param permission 权限代码
     * @return 有权限的组织ID列表
     */
    List<Long> getUserAuthorizedOrganizations(Long userId, String permission);

    /**
     * 获取组织有权限的用户列表
     *
     * @param organizationId 组织ID
     * @param permission 权限代码
     * @return 有权限的用户ID列表
     */
    List<Long> getOrganizationAuthorizedUsers(Long organizationId, String permission);

    /**
     * 授予用户组织权限
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @param permission 权限代码
     */
    void grantOrganizationPermission(Long userId, Long organizationId, String permission);

    /**
     * 撤销用户组织权限
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @param permission 权限代码
     */
    void revokeOrganizationPermission(Long userId, Long organizationId, String permission);

    /**
     * 检查用户是否是组织管理员
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @return 是否是管理员
     */
    boolean isOrganizationAdmin(Long userId, Long organizationId);

    /**
     * 检查用户是否是组织成员
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @return 是否是成员
     */
    boolean isOrganizationMember(Long userId, Long organizationId);

    /**
     * 获取用户在组织中的所有权限
     *
     * @param userId 用户ID
     * @param organizationId 组织ID
     * @return 权限列表
     */
    List<String> getUserOrganizationPermissions(Long userId, Long organizationId);
} 