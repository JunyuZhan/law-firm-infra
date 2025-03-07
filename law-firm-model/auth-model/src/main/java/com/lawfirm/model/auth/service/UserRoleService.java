package com.lawfirm.model.auth.service;

import java.util.List;

/**
 * 用户角色服务接口
 */
public interface UserRoleService {
    
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
     * 查询用户的权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> getUserPermissions(Long userId);
} 