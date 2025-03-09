package com.lawfirm.model.personnel.service;

import java.util.List;

/**
 * 员工与认证模块的桥接接口
 * 用于处理员工与用户认证之间的关联
 */
public interface EmployeeAuthBridge {

    /**
     * 根据用户ID获取员工ID
     *
     * @param userId 用户ID
     * @return 员工ID
     */
    Long getEmployeeIdByUserId(Long userId);
    
    /**
     * 根据员工ID获取用户ID
     *
     * @param employeeId 员工ID
     * @return 用户ID
     */
    Long getUserIdByEmployeeId(Long employeeId);
    
    /**
     * 获取员工的所有角色代码
     *
     * @param employeeId 员工ID
     * @return 角色代码列表
     */
    List<String> getEmployeeRoleCodes(Long employeeId);
    
    /**
     * 获取员工的所有权限代码
     *
     * @param employeeId 员工ID
     * @return 权限代码列表
     */
    List<String> getEmployeePermissionCodes(Long employeeId);
    
    /**
     * 检查员工是否拥有特定权限
     *
     * @param employeeId 员工ID
     * @param permissionCode 权限代码
     * @return 是否拥有权限
     */
    boolean hasPermission(Long employeeId, String permissionCode);
    
    /**
     * 添加员工与用户的关联
     *
     * @param employeeId 员工ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean linkEmployeeWithUser(Long employeeId, Long userId);
    
    /**
     * 解除员工与用户的关联
     *
     * @param employeeId 员工ID
     * @return 是否成功
     */
    boolean unlinkEmployeeFromUser(Long employeeId);
    
    /**
     * 从员工信息创建用户账户
     *
     * @param employeeId 员工ID
     * @param initialPassword 初始密码
     * @return 创建的用户ID
     */
    Long createUserFromEmployee(Long employeeId, String initialPassword);
    
    /**
     * 同步员工基本信息到用户账户
     *
     * @param employeeId 员工ID
     * @return 是否成功
     */
    boolean syncEmployeeToUser(Long employeeId);
} 