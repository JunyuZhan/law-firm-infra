package com.lawfirm.model.auth.service;

import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;

/**
 * 权限检查接口
 * 用于检查用户对特定模块的操作权限和数据范围
 */
public interface PermissionChecker {
    
    /**
     * 检查用户是否有操作特定模块的权限
     *
     * @param userId 用户ID
     * @param moduleCode 模块编码
     * @param operationType 操作类型
     * @return 是否有权限
     */
    boolean hasPermission(Long userId, String moduleCode, OperationTypeEnum operationType);
    
    /**
     * 获取用户对特定模块的数据范围
     *
     * @param userId 用户ID
     * @param moduleCode 模块编码
     * @return 数据范围
     */
    DataScopeEnum getDataScope(Long userId, String moduleCode);
    
    /**
     * 检查用户是否拥有某个角色
     *
     * @param userId 用户ID
     * @param roleCode 角色编码
     * @return 是否拥有该角色
     */
    boolean hasRole(Long userId, String roleCode);
    
    /**
     * 检查用户是否拥有某个权限
     *
     * @param userId 用户ID
     * @param permissionCode 权限编码
     * @return 是否拥有该权限
     */
    boolean hasPermission(Long userId, String permissionCode);
} 