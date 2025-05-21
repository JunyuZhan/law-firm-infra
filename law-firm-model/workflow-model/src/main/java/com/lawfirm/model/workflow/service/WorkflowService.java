package com.lawfirm.model.workflow.service;

import java.util.Map;
import java.util.List;

/**
 * 工作流服务接口
 * <p>
 * 定义工作流通用服务接口，独立于实际工作流引擎实现
 * </p>
 */
public interface WorkflowService {
    
    /**
     * 启动流程
     *
     * @param processDefinitionKey 流程定义键
     * @param businessKey 业务键
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables);
    
    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     */
    void completeTask(String taskId, Map<String, Object> variables);
    
    /**
     * 查询指定用户的任务
     *
     * @param assignee 受理人
     * @return 任务列表
     */
    List<Map<String, Object>> queryTasks(String assignee);
    
    /**
     * 查询流程实例状态
     *
     * @param processInstanceId 流程实例ID
     * @return 流程状态
     */
    String getProcessStatus(String processInstanceId);
    
    /**
     * 判断工作流是否可用
     *
     * @return 工作流可用状态
     */
    boolean isWorkflowEnabled();
} 