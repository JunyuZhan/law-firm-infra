package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Flowable配置类
 * <p>
 * 当flowable.enabled=false时提供空实现，避免Spring Boot自动配置
 * 尝试创建Flowable相关组件，降低应用启动依赖。
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "flowable.enabled", havingValue = "false", matchIfMissing = true)
public class FlowableConfig {

    /**
     * 创建Flowable禁用标记
     */
    @Bean
    public String flowableDisabled() {
        log.info("Flowable工作流功能已禁用，使用存根实现");
        return "flowableDisabled";
    }

    /**
     * 空的Flowable引擎实现
     */
    @Bean("processEngine")
    @Primary
    @ConditionalOnMissingBean(name = "processEngine")
    public Object processEngine() {
        return new HashMap<>();
    }

    /**
     * 空的RuntimeService实现
     */
    @Bean("runtimeService")
    @Primary
    @ConditionalOnMissingBean(name = "runtimeService")
    public Object runtimeService() {
        return new HashMap<>();
    }

    /**
     * 空的TaskService实现
     */
    @Bean("taskService")
    @Primary
    @ConditionalOnMissingBean(name = "taskService")
    public Object taskService() {
        return new HashMap<>();
    }

    /**
     * 空的RepositoryService实现
     */
    @Bean("repositoryService")
    @Primary
    @ConditionalOnMissingBean(name = "repositoryService")
    public Object repositoryService() {
        return new HashMap<>();
    }

    /**
     * 空的FormService实现
     */
    @Bean("formService")
    @Primary
    @ConditionalOnMissingBean(name = "formService")
    public Object formService() {
        return new HashMap<>();
    }

    /**
     * 空的HistoryService实现
     */
    @Bean("historyService")
    @Primary
    @ConditionalOnMissingBean(name = "historyService")
    public Object historyService() {
        return new HashMap<>();
    }

    /**
     * 空的ManagementService实现
     */
    @Bean("managementService")
    @Primary
    @ConditionalOnMissingBean(name = "managementService")
    public Object managementService() {
        return new HashMap<>();
    }

    /**
     * 空的DynamicBpmnService实现
     */
    @Bean("dynamicBpmnService")
    @Primary
    @ConditionalOnMissingBean(name = "dynamicBpmnService")
    public Object dynamicBpmnService() {
        return new HashMap<>();
    }

    /**
     * 空的ProcessMigrationService实现
     */
    @Bean("processMigrationService")
    @Primary
    @ConditionalOnMissingBean(name = "processMigrationService")
    public Object processMigrationService() {
        return new HashMap<>();
    }
} 