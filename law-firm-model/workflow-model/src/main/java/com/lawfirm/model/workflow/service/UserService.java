package com.lawfirm.model.workflow.service;

import java.util.List;

/**
 * 用户服务接口
 * 提供用户相关的功能
 *
 * @author claude
 */
public interface UserService {

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return 用户名
     */
    String getUserName(Long userId);

    /**
     * 根据角色ID获取用户列表
     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> getUsersByRole(Long roleId);

    /**
     * 根据部门ID获取用户列表
     *
     * @param deptId 部门ID
     * @return 用户ID列表
     */
    List<Long> getUsersByDept(Long deptId);

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getUserRoles(Long userId);

    /**
     * 获取用户的部门列表
     *
     * @param userId 用户ID
     * @return 部门ID列表
     */
    List<Long> getUserDepts(Long userId);

    /**
     * 检查用户是否有指定权限
     *
     * @param userId 用户ID
     * @param permission 权限标识
     * @return 是否有权限
     */
    boolean hasPermission(Long userId, String permission);
} 