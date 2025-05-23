package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 工作流自动配置类
 */
@AutoConfiguration
@Import({
    FlowableConfig.class,
    WorkflowConfig.class,
    CacheConfig.class
})
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class WorkflowAutoConfiguration {

    /**
     * 创建工作流配置Bean
     * 
     * @param provider 工作流配置提供者
     * @return 工作流配置属性
     */
    @Bean(name = "workflowProperties")
    @ConditionalOnBean(WorkflowPropertiesProvider.class)
    public WorkflowProperties workflowProperties(WorkflowPropertiesProvider provider) {
        return provider.getWorkflowProperties();
    }
    
    /**
     * 创建默认的工作流配置Bean，当业务层没有提供配置时使用
     * 
     * @return 默认的工作流配置属性
     */
    @Bean(name = "defaultWorkflowProperties")
    @ConditionalOnMissingBean(WorkflowPropertiesProvider.class)
    public WorkflowProperties defaultWorkflowProperties() {
        WorkflowProperties properties = new WorkflowProperties();
        properties.setEnabled(false); // 确保默认禁用
        return properties;
    }
}