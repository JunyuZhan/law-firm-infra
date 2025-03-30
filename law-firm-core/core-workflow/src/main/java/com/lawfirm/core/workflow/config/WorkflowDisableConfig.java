package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 工作流禁用配置
 * <p>
 * 负责处理工作流相关的类型依赖缺失情况
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnMissingClass({
    "org.flowable.spring.boot.EngineConfigurationConfigurer",
    "org.flowable.engine.ProcessEngine"
})
@ConditionalOnProperty(name = "lawfirm.workflow.enabled", havingValue = "false", matchIfMissing = false)
public class WorkflowDisableConfig {
    
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
        log.info("工作流包路径设置为禁用路径");
        return "com.lawfirm.core.workflow.disabled";
    }
} 