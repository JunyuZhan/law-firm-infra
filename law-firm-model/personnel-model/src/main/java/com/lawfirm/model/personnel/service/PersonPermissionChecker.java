package com.lawfirm.model.personnel.service;

import com.lawfirm.model.personnel.enums.PersonRoleEnum;

/**
 * 律师事务所人员权限检查器
 * 定义人员角色相关的权限检查方法
 */
public interface PersonPermissionChecker {
    
    /**
     * 获取用户的人员角色
     *
     * @param userId 用户ID
     * @return 人员角色
     */
    PersonRoleEnum getPersonRole(Long userId);
    
    /**
     * 检查用户是否有指定模块的操作权限
     *
     * @param userId 用户ID
     * @param moduleCode 模块编码
     * @param operationType 操作类型编码 (FULL/READ_ONLY/PERSONAL/APPROVE/APPLY)
     * @return 是否有权限
     */
    boolean hasModulePermission(Long userId, String moduleCode, String operationType);
    
    /**
     * 获取用户对指定模块的数据范围
     *
     * @param userId 用户ID
     * @param moduleCode 模块编码
     * @return 数据范围编码 (ALL/TEAM/PERSONAL/CUSTOM)
     */
    String getModuleDataScope(Long userId, String moduleCode);
    
    /**
     * 检查用户是否有指定人员角色
     *
     * @param userId 用户ID
     * @param roleCode 角色编码
     * @return 是否拥有该角色
     */
    boolean hasPersonRole(Long userId, String roleCode);
} 