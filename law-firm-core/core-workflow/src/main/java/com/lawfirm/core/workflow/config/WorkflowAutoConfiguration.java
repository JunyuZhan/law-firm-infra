package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 工作流模块自动配置类
 * 负责加载工作流模块的所有配置
 */
@AutoConfiguration
@Import({
    FlowableConfig.class,
    CacheConfig.class,
    WorkflowConfig.class
})
@ConditionalOnProperty(prefix = "workflow", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WorkflowAutoConfiguration {

    /**
     * 创建工作流配置Bean
     */
    @Bean
    public WorkflowProperties workflowProperties() {
        return new WorkflowProperties();
    }
} 