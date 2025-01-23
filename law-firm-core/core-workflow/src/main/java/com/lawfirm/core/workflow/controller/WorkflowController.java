package com.lawfirm.core.workflow.controller;

import com.lawfirm.core.workflow.model.ProcessDefinition;
import com.lawfirm.core.workflow.model.ProcessInstance;
import com.lawfirm.core.workflow.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 工作流控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {
    
    private final WorkflowService workflowService;
    
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }
    
    /**
     * 部署流程定义
     */
    @PostMapping("/process-definitions")
    public ProcessDefinition deploy(@RequestParam("file") MultipartFile file,
                                  @RequestParam("name") String name,
                                  @RequestParam(value = "category", required = false) String category,
                                  @RequestParam(value = "tenantId", required = false) String tenantId) {
        return workflowService.deploy(file, name, category, tenantId);
    }
    
    /**
     * 删除流程定义
     */
    @DeleteMapping("/process-definitions/{deploymentId}")
    public void deleteDeployment(@PathVariable String deploymentId,
                               @RequestParam(value = "cascade", defaultValue = "false") boolean cascade) {
        workflowService.deleteDeployment(deploymentId, cascade);
    }
    
    /**
     * 获取流程定义
     */
    @GetMapping("/process-definitions/{processDefinitionId}")
    public ProcessDefinition getProcessDefinition(@PathVariable String processDefinitionId) {
        return workflowService.getProcessDefinition(processDefinitionId);
    }
    
    /**
     * 查询流程定义列表
     */
    @GetMapping("/process-definitions")
    public List<ProcessDefinition> listProcessDefinitions(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "latestVersion", defaultValue = "true") boolean latestVersion) {
        return workflowService.listProcessDefinitions(key, name, category, tenantId, latestVersion);
    }
    
    /**
     * 启动流程实例
     */
    @PostMapping("/process-instances")
    public ProcessInstance startProcess(@RequestParam String processDefinitionId,
                                      @RequestParam(required = false) String businessKey,
                                      @RequestBody(required = false) Map<String, Object> variables,
                                      @RequestParam String startUserId) {
        return workflowService.startProcess(processDefinitionId, businessKey, variables, startUserId);
    }
    
    /**
     * 通过流程定义键启动流程实例
     */
    @PostMapping("/process-instances/by-key")
    public ProcessInstance startProcessByKey(@RequestParam String processDefinitionKey,
                                           @RequestParam(required = false) String businessKey,
                                           @RequestBody(required = false) Map<String, Object> variables,
                                           @RequestParam String startUserId,
                                           @RequestParam(required = false) String tenantId) {
        return workflowService.startProcessByKey(processDefinitionKey, businessKey, variables, startUserId, tenantId);
    }
    
    /**
     * 挂起流程实例
     */
    @PostMapping("/process-instances/{processInstanceId}/suspend")
    public void suspendProcessInstance(@PathVariable String processInstanceId) {
        workflowService.suspendProcessInstance(processInstanceId);
    }
    
    /**
     * 激活流程实例
     */
    @PostMapping("/process-instances/{processInstanceId}/activate")
    public void activateProcessInstance(@PathVariable String processInstanceId) {
        workflowService.activateProcessInstance(processInstanceId);
    }
    
    /**
     * 删除流程实例
     */
    @DeleteMapping("/process-instances/{processInstanceId}")
    public void deleteProcessInstance(@PathVariable String processInstanceId,
                                    @RequestParam(required = false) String deleteReason) {
        workflowService.deleteProcessInstance(processInstanceId, deleteReason);
    }
    
    /**
     * 获取流程实例
     */
    @GetMapping("/process-instances/{processInstanceId}")
    public ProcessInstance getProcessInstance(@PathVariable String processInstanceId) {
        return workflowService.getProcessInstance(processInstanceId);
    }
    
    /**
     * 查询流程实例列表
     */
    @GetMapping("/process-instances")
    public List<ProcessInstance> listProcessInstances(
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "startUserId", required = false) String startUserId,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return workflowService.listProcessInstances(processDefinitionKey, businessKey, startUserId, tenantId, onlyActive);
    }
    
    /**
     * 获取流程变量
     */
    @GetMapping("/process-instances/{processInstanceId}/variables/{variableName}")
    public Object getVariable(@PathVariable String processInstanceId,
                            @PathVariable String variableName) {
        return workflowService.getVariable(processInstanceId, variableName);
    }
    
    /**
     * 获取所有流程变量
     */
    @GetMapping("/process-instances/{processInstanceId}/variables")
    public Map<String, Object> getVariables(@PathVariable String processInstanceId) {
        return workflowService.getVariables(processInstanceId);
    }
    
    /**
     * 设置流程变量
     */
    @PutMapping("/process-instances/{processInstanceId}/variables/{variableName}")
    public void setVariable(@PathVariable String processInstanceId,
                          @PathVariable String variableName,
                          @RequestBody Object value) {
        workflowService.setVariable(processInstanceId, variableName, value);
    }
    
    /**
     * 批量设置流程变量
     */
    @PutMapping("/process-instances/{processInstanceId}/variables")
    public void setVariables(@PathVariable String processInstanceId,
                           @RequestBody Map<String, Object> variables) {
        workflowService.setVariables(processInstanceId, variables);
    }
    
    /**
     * 删除流程变量
     */
    @DeleteMapping("/process-instances/{processInstanceId}/variables/{variableName}")
    public void removeVariable(@PathVariable String processInstanceId,
                             @PathVariable String variableName) {
        workflowService.removeVariable(processInstanceId, variableName);
    }
    
    /**
     * 批量删除流程变量
     */
    @DeleteMapping("/process-instances/{processInstanceId}/variables")
    public void removeVariables(@PathVariable String processInstanceId,
                              @RequestBody List<String> variableNames) {
        workflowService.removeVariables(processInstanceId, variableNames);
    }
} 