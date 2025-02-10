package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.ProcessPermission;
import com.lawfirm.core.workflow.model.TaskNodePermission;

import java.util.List;

/**
 * 流程权限服务
 */
public interface ProcessPermissionService {
    
    /**
     * 创建流程权限
     *
     * @param permission 流程权限
     * @return 流程权限
     */
    ProcessPermission createPermission(ProcessPermission permission);
    
    /**
     * 更新流程权限
     *
     * @param permission 流程权限
     * @return 流程权限
     */
    ProcessPermission updatePermission(ProcessPermission permission);
    
    /**
     * 删除流程权限
     *
     * @param processKey 流程定义Key
     */
    void deletePermission(String processKey);
    
    /**
     * 获取流程权限
     *
     * @param processKey 流程定义Key
     * @return 流程权限
     */
    ProcessPermission getPermission(String processKey);
    
    /**
     * 查询流程权限列表
     *
     * @param category 流程分类
     * @param enabled 是否启用
     * @return 流程权限列表
     */
    List<ProcessPermission> listPermissions(String category, Boolean enabled);
    
    /**
     * 检查流程发起权限
     *
     * @param processKey 流程定义Key
     * @param userId 用户ID
     * @return 是否有权限
     */
    boolean checkStartPermission(String processKey, String userId);
    
    /**
     * 检查任务处理权限
     *
     * @param processKey 流程定义Key
     * @param taskKey 任务定义Key
     * @param userId 用户ID
     * @return 是否有权限
     */
    boolean checkTaskPermission(String processKey, String taskKey, String userId);
    
    /**
     * 获取可发起流程列表
     *
     * @param userId 用户ID
     * @return 流程定义Key列表
     */
    List<String> getStartableProcesses(String userId);
    
    /**
     * 获取任务候选人列表
     *
     * @param processKey 流程定义Key
     * @param taskKey 任务定义Key
     * @return 用户ID列表
     */
    List<String> getTaskCandidates(String processKey, String taskKey);
} 