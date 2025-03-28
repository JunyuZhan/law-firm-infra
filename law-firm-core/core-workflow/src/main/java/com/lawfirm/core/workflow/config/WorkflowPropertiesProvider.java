package com.lawfirm.core.workflow.config;

/**
 * 工作流配置提供者接口
 * 业务层实现此接口以提供工作流配置
 */
public interface WorkflowPropertiesProvider {
    
    /**
     * 获取工作流配置
     * 
     * @return 工作流配置属性
     */
    WorkflowProperties getWorkflowProperties();
} 