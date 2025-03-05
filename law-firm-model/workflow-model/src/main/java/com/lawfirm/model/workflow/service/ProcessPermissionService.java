package com.lawfirm.model.workflow.service;

import java.util.List;
import java.util.Map;

/**
 * 流程权限服务接口
 * 提供流程权限管理相关的所有业务功能
 *
 * @author JunyuZhan
 */
public interface ProcessPermissionService {
    /**
     * 创建流程权限
     *
     * @param permission 权限实体
     * @return 权限ID
     */
    Long createPermission(Map<String, Object> permission);

    /**
     * 批量创建流程权限
     *
     * @param permissions 权限实体列表
     * @return 成功创建的数量
     */
    int batchCreatePermissions(List<Map<String, Object>> permissions);

    /**
     * 更新流程权限
     *
     * @param permission 权限实体
     * @return 是否成功
     */
    boolean updatePermission(Map<String, Object> permission);

    /**
     * 删除流程权限
     *
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean deletePermission(Long permissionId);

    /**
     * 批量删除流程权限
     *
     * @param permissionIds 权限ID列表
     * @return 成功删除的数量
     */
    int batchDeletePermissions(List<Long> permissionIds);

    /**
     * 根据权限ID查询权限
     *
     * @param permissionId 权限ID
     * @return 权限实体
     */
    Map<String, Object> getPermissionById(Long permissionId);

    /**
     * 分页查询流程权限
     *
     * @param pageNum           页码
     * @param pageSize          每页大小
     * @param processDefinitionKey 流程定义键
     * @param permissionType    权限类型
     * @param operationType     操作类型
     * @param targetType        目标类型
     * @param targetId          目标ID
     * @return 权限分页列表
     */
    Map<String, Object> getPermissionPage(int pageNum, int pageSize, String processDefinitionKey,
                                          Integer permissionType, Integer operationType,
                                          Integer targetType, Long targetId);

    /**
     * 为用户分配流程定义权限
     *
     * @param userId             用户ID
     * @param processDefinitionKey 流程定义键
     * @param operationType      操作类型
     * @param permissionPolicy   权限策略
     * @return 权限ID
     */
    Long assignUserDefinitionPermission(Long userId, String processDefinitionKey, Integer operationType, Integer permissionPolicy);

    /**
     * 为角色分配流程定义权限
     *
     * @param roleId             角色ID
     * @param processDefinitionKey 流程定义键
     * @param operationType      操作类型
     * @param permissionPolicy   权限策略
     * @return 权限ID
     */
    Long assignRoleDefinitionPermission(Long roleId, String processDefinitionKey, Integer operationType, Integer permissionPolicy);

    /**
     * 为部门分配流程定义权限
     *
     * @param deptId             部门ID
     * @param processDefinitionKey 流程定义键
     * @param operationType      操作类型
     * @param permissionPolicy   权限策略
     * @return 权限ID
     */
    Long assignDeptDefinitionPermission(Long deptId, String processDefinitionKey, Integer operationType, Integer permissionPolicy);

    /**
     * 为流程指定启动权限
     *
     * @param processDefinitionKey 流程定义键
     * @param targetType          目标类型
     * @param targetIds           目标ID列表
     * @param permissionPolicy    权限策略
     * @return 成功创建的权限数量
     */
    int assignStartPermission(String processDefinitionKey, Integer targetType, List<Long> targetIds, Integer permissionPolicy);

    /**
     * 检查用户是否有启动流程的权限
     *
     * @param userId             用户ID
     * @param processDefinitionKey 流程定义键
     * @return 是否有权限
     */
    boolean checkStartProcessPermission(Long userId, String processDefinitionKey);

    /**
     * 检查用户是否有查看流程的权限
     *
     * @param userId             用户ID
     * @param processDefinitionKey 流程定义键
     * @return 是否有权限
     */
    boolean checkViewProcessPermission(Long userId, String processDefinitionKey);

    /**
     * 检查用户是否有取消流程的权限
     *
     * @param userId             用户ID
     * @param processDefinitionKey 流程定义键
     * @return 是否有权限
     */
    boolean checkCancelProcessPermission(Long userId, String processDefinitionKey);

    /**
     * 删除流程定义相关的所有权限
     *
     * @param processDefinitionKey 流程定义键
     */
    void deletePermissionsByProcessDefinitionKey(String processDefinitionKey);
} 
