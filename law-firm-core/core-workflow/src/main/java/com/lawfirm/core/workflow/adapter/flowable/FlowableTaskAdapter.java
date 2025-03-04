package com.lawfirm.core.workflow.adapter.flowable;

import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;
import java.util.Map;

/**
 * Flowable任务引擎适配器接�? * 用于适配Flowable任务引擎API，屏蔽底层实�? * 
 * @author JunyuZhan
 */
public interface FlowableTaskAdapter {

    /**
     * 创建任务
     * 
     * @param taskName 任务名称
     * @param assignee 处理�?     * @param processInstanceId 流程实例ID
     * @param variables 任务变量
     * @return 任务实例
     */
    Task createTask(String taskName, String assignee, String processInstanceId, Map<String, Object> variables);

    /**
     * 获取任务
     * 
     * @param taskId 任务ID
     * @return 任务实例
     */
    Task getTask(String taskId);
    
    /**
     * 获取历史任务
     * 
     * @param taskId 任务ID
     * @return 历史任务实例
     */
    HistoricTaskInstance getHistoricTask(String taskId);

    /**
     * 完成任务
     * 
     * @param taskId 任务ID
     * @param variables 任务变量
     */
    void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 删除任务
     * 
     * @param taskId 任务ID
     * @param reason 删除原因
     */
    void deleteTask(String taskId, String reason);

    /**
     * 查询流程实例的任�?     * 
     * @param processInstanceId 流程实例ID
     * @return 任务列表
     */
    List<Task> listProcessTasks(String processInstanceId);

    /**
     * 查询用户待办任务
     * 
     * @param assignee 处理�?     * @return 任务列表
     */
    List<Task> listUserTodoTasks(String assignee);

    /**
     * 查询用户已办任务
     * 
     * @param assignee 处理�?     * @return 历史任务列表
     */
    List<HistoricTaskInstance> listUserDoneTasks(String assignee);

    /**
     * 查询组任�?     * 
     * @param candidateGroup 候选组
     * @return 任务列表
     */
    List<Task> listGroupTasks(String candidateGroup);

    /**
     * 转办任务
     * 
     * @param taskId 任务ID
     * @param assignee 新处理人
     */
    void transferTask(String taskId, String assignee);

    /**
     * 委派任务
     * 
     * @param taskId 任务ID
     * @param assignee 被委派人
     */
    void delegateTask(String taskId, String assignee);

    /**
     * 设置过期时间
     * 
     * @param taskId 任务ID
     * @param dueDate 过期时间
     */
    void setDueDate(String taskId, java.util.Date dueDate);

    /**
     * 添加候选人
     * 
     * @param taskId 任务ID
     * @param candidateUser 候选人
     */
    void addCandidateUser(String taskId, String candidateUser);

    /**
     * 添加候选组
     * 
     * @param taskId 任务ID
     * @param candidateGroup 候选组
     */
    void addCandidateGroup(String taskId, String candidateGroup);
    
    /**
     * 设置任务优先�?     * 
     * @param taskId 任务ID
     * @param priority 优先�?     */
    void setPriority(String taskId, int priority);
    
    /**
     * 获取任务变量
     * 
     * @param taskId 任务ID
     * @param variableName 变量�?     * @return 变量�?     */
    Object getTaskVariable(String taskId, String variableName);
    
    /**
     * 设置任务变量
     * 
     * @param taskId 任务ID
     * @param variableName 变量�?     * @param value 变量�?     */
    void setTaskVariable(String taskId, String variableName, Object value);
} 
