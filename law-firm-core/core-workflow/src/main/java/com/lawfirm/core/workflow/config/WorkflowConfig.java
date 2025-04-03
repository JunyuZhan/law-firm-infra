package com.lawfirm.core.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 工作流模块配置类
 * 
 * @author JunyuZhan
 */
@Slf4j
@Configuration
@EnableAsync
@ComponentScan(basePackages = {
    "com.lawfirm.core.workflow.adapter",
    "com.lawfirm.core.workflow.handler",
    "com.lawfirm.core.workflow.listener",
    "com.lawfirm.core.workflow.service"
})
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class WorkflowConfig {
    
    public WorkflowConfig() {
        log.info("初始化工作流模块配置");
    }
} 
