package com.lawfirm.core.workflow.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.service.DiagramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 流程图服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DiagramServiceImpl implements DiagramService {
    
    private final RepositoryService repositoryService;
    private final ObjectMapper objectMapper;
    
    @Override
    public InputStream getProcessDefinitionDiagram(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            return null;
        }
        return repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(),
                processDefinition.getDiagramResourceName()
        );
    }
    
    @Override
    public InputStream getProcessInstanceDiagram(String processInstanceId) {
        // TODO: 实现获取流程实例图(带高亮)的逻辑
        return null;
    }
    
    @Override
    public InputStream getProcessDefinitionXml(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            return null;
        }
        return repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(),
                processDefinition.getResourceName()
        );
    }
    
    @Override
    public String getProcessDefinitionDiagramResourceName(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return processDefinition != null ? processDefinition.getDiagramResourceName() : null;
    }
    
    @Override
    public String getProcessDefinitionXmlResourceName(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return processDefinition != null ? processDefinition.getResourceName() : null;
    }
    
    @Override
    public boolean validateProcessDefinitionXml(InputStream xmlInputStream) {
        try {
            // 尝试部署流程定义来验证
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream("temp.bpmn20.xml", xmlInputStream)
                    .deploy();
            
            // 验证成功后删除临时部署
            repositoryService.deleteDeployment(deployment.getId(), true);
            return true;
        } catch (Exception e) {
            log.error("Failed to validate process definition XML", e);
            return false;
        }
    }
} 