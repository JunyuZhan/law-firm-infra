package com.lawfirm.core.workflow.adapter.flowable;

import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * Flowable流程引擎适配器接�? * 用于适配Flowable流程引擎API，屏蔽底层实�? * 
 * @author JunyuZhan
 */
public interface FlowableProcessAdapter {

    /**
     * 启动流程
     * 
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * 启动流程
     * 
     * @param processDefinitionId 流程定义ID
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startProcessById(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 挂起流程实例
     * 
     * @param processInstanceId 流程实例ID
     */
    void suspendProcess(String processInstanceId);

    /**
     * 激活流程实�?     * 
     * @param processInstanceId 流程实例ID
     */
    void activateProcess(String processInstanceId);

    /**
     * 终止流程实例
     * 
     * @param processInstanceId 流程实例ID
     * @param reason 终止原因
     */
    void terminateProcess(String processInstanceId, String reason);

    /**
     * 删除流程实例
     * 
     * @param processInstanceId 流程实例ID
     * @param reason 删除原因
     */
    void deleteProcess(String processInstanceId, String reason);

    /**
     * 获取流程实例
     * 
     * @param processInstanceId 流程实例ID
     * @return 流程实例
     */
    ProcessInstance getProcessInstance(String processInstanceId);

    /**
     * 查询流程变量
     * 
     * @param processInstanceId 流程实例ID
     * @param variableName 变量�?     * @return 变量�?     */
    Object getVariable(String processInstanceId, String variableName);

    /**
     * 设置流程变量
     * 
     * @param processInstanceId 流程实例ID
     * @param variableName 变量�?     * @param value 变量�?     */
    void setVariable(String processInstanceId, String variableName, Object value);

    /**
     * 查询流程实例是否已结�?     * 
     * @param processInstanceId 流程实例ID
     * @return 是否已结�?     */
    boolean isProcessEnded(String processInstanceId);
} 
