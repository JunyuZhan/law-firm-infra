package com.lawfirm.auth.service;

/**
 * 权限缓存服务接口
 * <p>
 * 提供权限相关缓存的管理功能，包括缓存清理、刷新等操作。
 * </p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
public interface PermissionCacheService {

    /**
     * 清理指定用户的所有权限缓存
     * 
     * @param userId 用户ID
     */
    void clearUserPermissionCache(Long userId);

    /**
     * 清理指定角色的权限缓存
     * 
     * @param roleId 角色ID
     */
    void clearRolePermissionCache(Long roleId);

    /**
     * 清理指定团队的权限缓存
     * 
     * @param teamId 团队ID
     */
    void clearTeamPermissionCache(Long teamId);

    /**
     * 清理指定业务类型的权限缓存
     * 
     * @param businessType 业务类型
     */
    void clearBusinessPermissionCache(String businessType);

    /**
     * 清理所有权限缓存
     */
    void clearAllPermissionCache();

    /**
     * 预热权限缓存
     * 
     * @param userId 用户ID
     */
    void warmUpUserPermissionCache(Long userId);

    /**
     * 获取缓存统计信息
     * 
     * @return 缓存统计信息
     */
    String getCacheStatistics();
}