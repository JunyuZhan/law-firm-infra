package com.lawfirm.model.auth.service;

import java.util.List;

/**
 * 用户与人员关联服务接口
 * 作为auth-model和personnel-model之间的桥接层，实现模块间的松耦合集成
 * 遵循单一职责原则，专注于处理用户实体和员工实体之间的关联关系和信息同步
 */
public interface UserPersonnelService {
    
    /**
     * 关联用户和员工
     *
     * @param userId 用户ID
     * @param employeeId 员工ID
     * @return 是否关联成功
     */
    boolean linkUserToEmployee(Long userId, Long employeeId);
    
    /**
     * 解除用户和员工的关联
     *
     * @param userId 用户ID
     * @return 是否解除成功
     */
    boolean unlinkUserFromEmployee(Long userId);
    
    /**
     * 根据员工ID获取用户ID
     * 用于从personnel-model访问auth-model的场景
     *
     * @param employeeId 员工ID
     * @return 用户ID，如果不存在则返回null
     */
    Long getUserIdByEmployeeId(Long employeeId);
    
    /**
     * 根据用户ID获取员工ID
     * 用于从auth-model访问personnel-model的场景
     *
     * @param userId 用户ID
     * @return 员工ID，如果不存在则返回null
     */
    Long getEmployeeIdByUserId(Long userId);
    
    /**
     * 根据用户ID列表批量获取员工ID
     * 适用于需要批量处理用户-员工关联的场景
     *
     * @param userIds 用户ID列表
     * @return 员工ID列表，保持与入参顺序一致
     */
    List<Long> getEmployeeIdsByUserIds(List<Long> userIds);
    
    /**
     * 根据员工ID列表批量获取用户ID
     * 适用于需要批量处理员工-用户关联的场景
     *
     * @param employeeIds 员工ID列表
     * @return 用户ID列表，保持与入参顺序一致
     */
    List<Long> getUserIdsByEmployeeIds(List<Long> employeeIds);
    
    /**
     * 同步用户基本信息到员工
     * 例如：同步邮箱、手机号等通用信息
     * 保证auth-model和personnel-model中重复信息的一致性
     *
     * @param userId 用户ID
     * @return 是否同步成功
     */
    boolean syncUserInfoToEmployee(Long userId);
    
    /**
     * 同步员工基本信息到用户
     * 例如：同步邮箱、手机号等通用信息
     * 保证personnel-model和auth-model中重复信息的一致性
     *
     * @param employeeId 员工ID
     * @return 是否同步成功
     */
    boolean syncEmployeeInfoToUser(Long employeeId);
    
    /**
     * 检查用户是否已关联员工
     *
     * @param userId 用户ID
     * @return 是否已关联
     */
    boolean isUserLinkedToEmployee(Long userId);
    
    /**
     * 检查员工是否已关联用户
     *
     * @param employeeId 员工ID
     * @return 是否已关联
     */
    boolean isEmployeeLinkedToUser(Long employeeId);
} 