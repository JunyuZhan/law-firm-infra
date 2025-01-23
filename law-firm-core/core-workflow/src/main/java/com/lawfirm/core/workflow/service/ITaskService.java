package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;

import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 */
public interface ITaskService {
    
    /**
     * 获取任务
     */
    Task getTask(String taskId);
    
    /**
     * 查询任务列表
     */
    List<Task> listTasks(String processInstanceId, String taskDefinitionKey,
                        String assignee, String owner, String tenantId);
    
    /**
     * 认领任务
     */
    void claimTask(String taskId, String userId);
    
    /**
     * 取消认领任务
     */
    void unclaimTask(String taskId);
    
    /**
     * 完成任务
     */
    void completeTask(String taskId, Map<String, Object> variables);
    
    /**
     * 委派任务
     */
    void delegateTask(String taskId, String userId);
    
    /**
     * 转移任务
     */
    void transferTask(String taskId, String userId);
    
    /**
     * 设置任务处理人
     */
    void setAssignee(String taskId, String userId);
    
    /**
     * 添加候选用户
     */
    void addCandidateUser(String taskId, String userId);
    
    /**
     * 删除候选用户
     */
    void deleteCandidateUser(String taskId, String userId);
    
    /**
     * 添加候选组
     */
    void addCandidateGroup(String taskId, String groupId);
    
    /**
     * 删除候选组
     */
    void deleteCandidateGroup(String taskId, String groupId);
    
    /**
     * 获取历史任务
     */
    HistoricTask getHistoricTask(String taskId);
    
    /**
     * 查询历史任务列表
     */
    List<HistoricTask> listHistoricTasks(String processInstanceId, String taskDefinitionKey,
                                       String assignee, String owner, String tenantId, boolean finished);
} 