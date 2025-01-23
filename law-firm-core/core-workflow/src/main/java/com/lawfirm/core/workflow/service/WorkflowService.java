package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.ProcessDefinition;
import com.lawfirm.core.workflow.model.ProcessInstance;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 工作流服务接口
 */
public interface WorkflowService {
    
    /**
     * 部署流程定义
     *
     * @param file 流程定义文件
     * @param name 部署名称
     * @param category 流程分类
     * @param tenantId 租户ID
     * @return 流程定义
     */
    ProcessDefinition deploy(MultipartFile file, String name, String category, String tenantId);
    
    /**
     * 删除流程定义
     *
     * @param deploymentId 部署ID
     * @param cascade 是否级联删除
     */
    void deleteDeployment(String deploymentId, boolean cascade);
    
    /**
     * 获取流程定义
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);
    
    /**
     * 查询流程定义列表
     *
     * @param key 流程定义键
     * @param name 流程定义名称
     * @param category 流程分类
     * @param tenantId 租户ID
     * @param latestVersion 是否只查询最新版本
     * @return 流程定义列表
     */
    List<ProcessDefinition> listProcessDefinitions(String key, String name, String category,
                                                 String tenantId, boolean latestVersion);
    
    /**
     * 启动流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey 业务键
     * @param variables 流程变量
     * @param startUserId 启动用户ID
     * @return 流程实例
     */
    ProcessInstance startProcess(String processDefinitionId, String businessKey,
                               Map<String, Object> variables, String startUserId);
    
    /**
     * 启动流程实例（通过流程定义键）
     *
     * @param processDefinitionKey 流程定义键
     * @param businessKey 业务键
     * @param variables 流程变量
     * @param startUserId 启动用户ID
     * @param tenantId 租户ID
     * @return 流程实例
     */
    ProcessInstance startProcessByKey(String processDefinitionKey, String businessKey,
                                    Map<String, Object> variables, String startUserId, String tenantId);
    
    /**
     * 挂起流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void suspendProcessInstance(String processInstanceId);
    
    /**
     * 激活流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void activateProcessInstance(String processInstanceId);
    
    /**
     * 删除流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param deleteReason 删除原因
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason);
    
    /**
     * 获取流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例
     */
    ProcessInstance getProcessInstance(String processInstanceId);
    
    /**
     * 查询流程实例列表
     *
     * @param processDefinitionKey 流程定义键
     * @param businessKey 业务键
     * @param startUserId 启动用户ID
     * @param tenantId 租户ID
     * @param onlyActive 是否只查询活动的流程实例
     * @return 流程实例列表
     */
    List<ProcessInstance> listProcessInstances(String processDefinitionKey, String businessKey,
                                             String startUserId, String tenantId, boolean onlyActive);
    
    /**
     * 获取流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variableName 变量名称
     * @return 变量值
     */
    Object getVariable(String processInstanceId, String variableName);
    
    /**
     * 获取流程变量
     *
     * @param processInstanceId 流程实例ID
     * @return 变量列表
     */
    Map<String, Object> getVariables(String processInstanceId);
    
    /**
     * 设置流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variableName 变量名称
     * @param value 变量值
     */
    void setVariable(String processInstanceId, String variableName, Object value);
    
    /**
     * 设置流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variables 变量列表
     */
    void setVariables(String processInstanceId, Map<String, Object> variables);
    
    /**
     * 删除流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variableName 变量名称
     */
    void removeVariable(String processInstanceId, String variableName);
    
    /**
     * 删除流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variableNames 变量名称列表
     */
    void removeVariables(String processInstanceId, List<String> variableNames);
} 