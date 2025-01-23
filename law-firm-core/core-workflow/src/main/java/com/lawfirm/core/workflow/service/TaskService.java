package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;

import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 */
public interface TaskService {
    
    /**
     * 获取任务
     *
     * @param taskId 任务ID
     * @return 任务
     */
    Task getTask(String taskId);
    
    /**
     * 查询任务列表
     *
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义键
     * @param assignee 处理人
     * @param owner 所有者
     * @param tenantId 租户ID
     * @return 任务列表
     */
    List<Task> listTasks(String processInstanceId, String taskDefinitionKey,
                        String assignee, String owner, String tenantId);
    
    /**
     * 认领任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void claimTask(String taskId, String userId);
    
    /**
     * 取消认领任务
     *
     * @param taskId 任务ID
     */
    void unclaimTask(String taskId);
    
    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param variables 流程变量
     */
    void completeTask(String taskId, Map<String, Object> variables);
    
    /**
     * 委托任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void delegateTask(String taskId, String userId);
    
    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void transferTask(String taskId, String userId);
    
    /**
     * 设置任务处理人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void setAssignee(String taskId, String userId);
    
    /**
     * 添加任务候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void addCandidateUser(String taskId, String userId);
    
    /**
     * 删除任务候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void deleteCandidateUser(String taskId, String userId);
    
    /**
     * 添加任务候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     */
    void addCandidateGroup(String taskId, String groupId);
    
    /**
     * 删除任务候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     */
    void deleteCandidateGroup(String taskId, String groupId);
    
    /**
     * 获取历史任务
     *
     * @param taskId 任务ID
     * @return 历史任务
     */
    HistoricTask getHistoricTask(String taskId);
    
    /**
     * 查询历史任务列表
     *
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义键
     * @param assignee 处理人
     * @param owner 所有者
     * @param tenantId 租户ID
     * @param finished 是否已完成
     * @return 历史任务列表
     */
    List<HistoricTask> listHistoricTasks(String processInstanceId, String taskDefinitionKey,
                                       String assignee, String owner, String tenantId, boolean finished);
} 