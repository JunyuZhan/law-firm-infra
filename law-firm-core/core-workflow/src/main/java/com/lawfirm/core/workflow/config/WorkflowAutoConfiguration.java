package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 工作流自动配置类
 */
@AutoConfiguration
@EnableConfigurationProperties(WorkflowProperties.class)
@Import({
    FlowableConfig.class,
    WorkflowConfig.class,
    CacheConfig.class
})
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class WorkflowAutoConfiguration {

    /**
     * 创建工作流配置Bean
     */
    @Bean
    public WorkflowProperties workflowProperties() {
        return new WorkflowProperties();
    }
} 