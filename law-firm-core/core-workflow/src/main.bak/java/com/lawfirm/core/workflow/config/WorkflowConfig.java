package com.lawfirm.core.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 工作流模块配置类
 * 
 * @author JunyuZhan
 */
@Slf4j
@Configuration
@EnableAsync
@EntityScan(basePackages = {
    "com.lawfirm.core.workflow.entity",
    "com.lawfirm.model.workflow.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.lawfirm.core.workflow.repository"
})
@ComponentScan(basePackages = {
    "com.lawfirm.core.workflow.adapter",
    "com.lawfirm.core.workflow.handler",
    "com.lawfirm.core.workflow.listener",
    "com.lawfirm.core.workflow.service"
})
public class WorkflowConfig {
    
    public WorkflowConfig() {
        log.info("初始化工作流模块配置");
    }
} 
