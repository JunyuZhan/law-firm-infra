package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.auth.entity.UserRole;

import java.util.List;

/**
 * 用户角色关联服务接口 - 仅包含基础关联管理
 */
public interface UserRoleService extends IService<UserRole> {
    
    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getRoleIdsByUserId(Long userId);
    
    /**
     * 更新用户角色关联
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    boolean updateUserRoles(Long userId, List<Long> roleIds);
    
    /**
     * 为用户分配角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    boolean assignRoles(Long userId, List<Long> roleIds);
    
    /**
     * 移除用户的所有角色
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean removeUserRoles(Long userId);
    
    /**
     * 移除角色下的所有用户
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean removeRoleUsers(Long roleId);
    
    /**
     * 根据角色ID获取用户ID列表
     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> getUserIdsByRoleId(Long roleId);
} 