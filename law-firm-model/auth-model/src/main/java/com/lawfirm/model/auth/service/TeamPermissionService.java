package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.auth.entity.TeamPermission;
import com.lawfirm.model.auth.vo.PermissionVO;

import java.util.List;

/**
 * 团队权限服务接口
 */
public interface TeamPermissionService extends IService<TeamPermission> {
    
    /**
     * 授予团队权限
     *
     * @param teamId 团队ID
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean grantTeamPermission(Long teamId, Long permissionId);
    
    /**
     * 撤销团队权限
     *
     * @param teamId 团队ID
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean revokeTeamPermission(Long teamId, Long permissionId);
    
    /**
     * 根据团队ID获取权限列表
     *
     * @param teamId 团队ID
     * @return 权限列表
     */
    List<PermissionVO> listPermissionsByTeamId(Long teamId);
    
    /**
     * 检查团队是否有指定权限
     *
     * @param teamId 团队ID
     * @param permissionCode 权限编码
     * @return 是否有权限
     */
    boolean hasTeamPermission(Long teamId, String permissionCode);
    
    /**
     * 批量授予团队权限
     *
     * @param teamId 团队ID
     * @param permissionIds 权限ID列表
     * @return 是否成功
     */
    boolean batchGrantTeamPermissions(Long teamId, List<Long> permissionIds);
    
    /**
     * 批量撤销团队权限
     *
     * @param teamId 团队ID
     * @param permissionIds 权限ID列表
     * @return 是否成功
     */
    boolean batchRevokeTeamPermissions(Long teamId, List<Long> permissionIds);
    
    /**
     * 获取用户通过团队拥有的权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionVO> listUserTeamPermissions(Long userId);
    
    /**
     * 授予用户在特定团队中对特定资源类型的权限
     *
     * @param teamId 团队ID
     * @param userId 用户ID
     * @param resourceType 资源类型
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean grantUserTeamResourcePermission(Long teamId, Long userId, String resourceType, Long permissionId);
    
    /**
     * 撤销用户在特定团队中对特定资源类型的权限
     *
     * @param teamId 团队ID
     * @param userId 用户ID
     * @param resourceType 资源类型
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean revokeUserTeamResourcePermission(Long teamId, Long userId, String resourceType, Long permissionId);
    
    /**
     * 检查用户是否在特定团队中拥有特定资源类型的权限
     *
     * @param teamId 团队ID
     * @param userId 用户ID
     * @param resourceType 资源类型
     * @return 是否有权限
     */
    boolean hasUserTeamResourcePermission(Long teamId, Long userId, String resourceType);
} 