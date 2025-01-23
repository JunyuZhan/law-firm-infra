package com.lawfirm.core.workflow.service;

import java.io.InputStream;

/**
 * 流程图服务接口
 */
public interface DiagramService {
    
    /**
     * 获取流程定义图
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图输入流
     */
    InputStream getProcessDefinitionDiagram(String processDefinitionId);
    
    /**
     * 获取流程实例图(带高亮)
     *
     * @param processInstanceId 流程实例ID
     * @return 流程图输入流
     */
    InputStream getProcessInstanceDiagram(String processInstanceId);
    
    /**
     * 获取流程定义XML
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义XML输入流
     */
    InputStream getProcessDefinitionXml(String processDefinitionId);
    
    /**
     * 获取流程定义图资源名称
     *
     * @param processDefinitionId 流程定义ID
     * @return 资源名称
     */
    String getProcessDefinitionDiagramResourceName(String processDefinitionId);
    
    /**
     * 获取流程定义XML资源名称
     *
     * @param processDefinitionId 流程定义ID
     * @return 资源名称
     */
    String getProcessDefinitionXmlResourceName(String processDefinitionId);
    
    /**
     * 验证流程定义XML
     *
     * @param xmlInputStream XML输入流
     * @return 是否有效
     */
    boolean validateProcessDefinitionXml(InputStream xmlInputStream);
} 