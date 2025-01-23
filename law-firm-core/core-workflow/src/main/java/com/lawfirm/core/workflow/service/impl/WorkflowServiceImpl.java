package com.lawfirm.core.workflow.service.impl;

import com.lawfirm.core.workflow.model.ProcessDefinition;
import com.lawfirm.core.workflow.model.ProcessInstance;
import com.lawfirm.core.workflow.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工作流服务实现
 */
@Slf4j
@Service
public class WorkflowServiceImpl implements WorkflowService {
    
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    
    public WorkflowServiceImpl(RepositoryService repositoryService, RuntimeService runtimeService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessDefinition deploy(MultipartFile file, String name, String category, String tenantId) {
        try {
            // 1. 部署流程定义
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream())
                    .name(name)
                    .category(category)
                    .tenantId(tenantId)
                    .deploy();
            
            // 2. 获取流程定义
            org.flowable.engine.repository.ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();
            
            // 3. 转换为自定义模型
            return convertToProcessDefinition(processDefinition);
        } catch (IOException e) {
            log.error("Failed to deploy process definition", e);
            throw new RuntimeException("Failed to deploy process definition", e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeployment(String deploymentId, boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade);
    }
    
    @Override
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        org.flowable.engine.repository.ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return convertToProcessDefinition(processDefinition);
    }
    
    @Override
    public List<ProcessDefinition> listProcessDefinitions(String key, String name, String category,
                                                        String tenantId, boolean latestVersion) {
        org.flowable.engine.repository.ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        
        if (StringUtils.hasText(key)) {
            query.processDefinitionKey(key);
        }
        if (StringUtils.hasText(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        if (StringUtils.hasText(category)) {
            query.processDefinitionCategory(category);
        }
        if (StringUtils.hasText(tenantId)) {
            query.processDefinitionTenantId(tenantId);
        }
        if (latestVersion) {
            query.latestVersion();
        }
        
        return query.orderByProcessDefinitionKey().asc()
                .orderByProcessDefinitionVersion().desc()
                .list()
                .stream()
                .map(this::convertToProcessDefinition)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstance startProcess(String processDefinitionId, String businessKey,
                                      Map<String, Object> variables, String startUserId) {
        org.flowable.engine.runtime.ProcessInstance processInstance = runtimeService
                .createProcessInstanceBuilder()
                .processDefinitionId(processDefinitionId)
                .businessKey(businessKey)
                .variables(variables)
                .start();
        return convertToProcessInstance(processInstance);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstance startProcessByKey(String processDefinitionKey, String businessKey,
                                           Map<String, Object> variables, String startUserId, String tenantId) {
        org.flowable.engine.runtime.ProcessInstance processInstance = runtimeService
                .createProcessInstanceBuilder()
                .processDefinitionKey(processDefinitionKey)
                .businessKey(businessKey)
                .variables(variables)
                .tenantId(tenantId)
                .start();
        return convertToProcessInstance(processInstance);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }
    
    @Override
    public ProcessInstance getProcessInstance(String processInstanceId) {
        org.flowable.engine.runtime.ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return convertToProcessInstance(processInstance);
    }
    
    @Override
    public List<ProcessInstance> listProcessInstances(String processDefinitionKey, String businessKey,
                                                    String startUserId, String tenantId, boolean onlyActive) {
        org.flowable.engine.runtime.ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        
        if (StringUtils.hasText(processDefinitionKey)) {
            query.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.hasText(businessKey)) {
            query.processInstanceBusinessKey(businessKey);
        }
        if (StringUtils.hasText(startUserId)) {
            query.startedBy(startUserId);
        }
        if (StringUtils.hasText(tenantId)) {
            query.processInstanceTenantId(tenantId);
        }
        if (onlyActive) {
            query.active();
        }
        
        return query.orderByProcessInstanceId().desc()
                .list()
                .stream()
                .map(this::convertToProcessInstance)
                .collect(Collectors.toList());
    }
    
    @Override
    public Object getVariable(String processInstanceId, String variableName) {
        return runtimeService.getVariable(processInstanceId, variableName);
    }
    
    @Override
    public Map<String, Object> getVariables(String processInstanceId) {
        return runtimeService.getVariables(processInstanceId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setVariable(String processInstanceId, String variableName, Object value) {
        runtimeService.setVariable(processInstanceId, variableName, value);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setVariables(String processInstanceId, Map<String, Object> variables) {
        runtimeService.setVariables(processInstanceId, variables);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeVariable(String processInstanceId, String variableName) {
        runtimeService.removeVariable(processInstanceId, variableName);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeVariables(String processInstanceId, List<String> variableNames) {
        runtimeService.removeVariables(processInstanceId, variableNames);
    }
    
    /**
     * 转换为流程定义模型
     */
    private ProcessDefinition convertToProcessDefinition(org.flowable.engine.repository.ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return null;
        }
        
        return new ProcessDefinition()
                .setId(processDefinition.getId())
                .setKey(processDefinition.getKey())
                .setName(processDefinition.getName())
                .setCategory(processDefinition.getCategory())
                .setVersion(processDefinition.getVersion())
                .setDeploymentId(processDefinition.getDeploymentId())
                .setResourceName(processDefinition.getResourceName())
                .setDiagramResourceName(processDefinition.getDiagramResourceName())
                .setDescription(processDefinition.getDescription())
                .setSuspended(processDefinition.isSuspended())
                .setTenantId(processDefinition.getTenantId())
                .setDeploymentTime(LocalDateTime.now())
                .setEngineVersion(processDefinition.getEngineVersion())
                .setHasStartForm(processDefinition.hasStartFormKey())
                .setHasGraphicalNotation(processDefinition.hasGraphicalNotation());
    }
    
    /**
     * 转换为流程实例模型
     */
    private ProcessInstance convertToProcessInstance(org.flowable.engine.runtime.ProcessInstance processInstance) {
        if (processInstance == null) {
            return null;
        }
        
        ProcessInstance instance = new ProcessInstance();
        instance.setId(processInstance.getId());
        instance.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        instance.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        instance.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        instance.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
        instance.setDeploymentId(processInstance.getDeploymentId());
        instance.setBusinessKey(processInstance.getBusinessKey());
        if (processInstance.getStartTime() != null) {
            instance.setStartTime(LocalDateTime.ofInstant(
                processInstance.getStartTime().toInstant(), 
                ZoneId.systemDefault()
            ));
        }
        instance.setStartUserId(processInstance.getStartUserId());
        instance.setSuspended(processInstance.isSuspended());
        instance.setEnded(processInstance.isEnded());
        instance.setVariables(getProcessVariables(processInstance));
        
        // 获取父部署ID
        String parentDeploymentId = getParentDeploymentId(processInstance);
        if (parentDeploymentId != null) {
            instance.setParentDeploymentId(parentDeploymentId);
        }
        
        return instance;
    }

    private Map<String, Object> getProcessVariables(org.flowable.engine.runtime.ProcessInstance processInstance) {
        return runtimeService.getVariables(processInstance.getId());
    }

    private String getParentDeploymentId(org.flowable.engine.runtime.ProcessInstance processInstance) {
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(processInstance.getDeploymentId())
                .singleResult();
        return deployment != null ? deployment.getParentDeploymentId() : null;
    }
} 