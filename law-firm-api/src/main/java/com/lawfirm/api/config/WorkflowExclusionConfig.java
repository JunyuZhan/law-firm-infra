package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 工作流排除配置
 * 负责处理工作流相关的类型依赖缺失情况
 */
@Slf4j
@Configuration
@ConditionalOnMissingClass({
    "org.flowable.spring.boot.EngineConfigurationConfigurer",
    "org.flowable.engine.ProcessEngine"
})
public class WorkflowExclusionConfig {
    
    /**
     * 配置工作流禁用标志
     */
    @Bean("workflowDisabled")
    public Boolean workflowDisabled() {
        log.info("工作流功能已完全禁用");
        return Boolean.TRUE;
    }
    
    /**
     * 处理工作流包路径
     */
    @Bean("workflowPackagePath")
    public String workflowPackagePath() {
        return "com.lawfirm.api.workflow.disabled";
    }
} 