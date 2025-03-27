package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * Flowable禁用配置
 * <p>
 * 提供空的Flowable服务实现，防止应用程序启动时尝试创建真正的Flowable组件
 */
@Configuration
@ConditionalOnProperty(name = "flowable.enabled", havingValue = "false", matchIfMissing = false)
public class NoFlowableConfig {

    /**
     * 空的Flowable引擎实现
     */
    @Bean("processEngine")
    @Primary
    @ConditionalOnMissingBean(name = "processEngine")
    public Object emptyProcessEngine() {
        return new HashMap<>();
    }

    /**
     * 空的RuntimeService实现
     */
    @Bean("runtimeService")
    @Primary
    @ConditionalOnMissingBean(name = "runtimeService")
    public Object emptyRuntimeService() {
        return new HashMap<>();
    }

    /**
     * 空的TaskService实现
     */
    @Bean("taskService")
    @Primary
    @ConditionalOnMissingBean(name = "taskService")
    public Object emptyTaskService() {
        return new HashMap<>();
    }

    /**
     * 空的RepositoryService实现
     */
    @Bean("repositoryService")
    @Primary
    @ConditionalOnMissingBean(name = "repositoryService")
    public Object emptyRepositoryService() {
        return new HashMap<>();
    }

    /**
     * 空的FormService实现
     */
    @Bean("formService")
    @Primary
    @ConditionalOnMissingBean(name = "formService")
    public Object emptyFormService() {
        return new HashMap<>();
    }

    /**
     * 空的HistoryService实现
     */
    @Bean("historyService")
    @Primary
    @ConditionalOnMissingBean(name = "historyService")
    public Object emptyHistoryService() {
        return new HashMap<>();
    }

    /**
     * 空的ManagementService实现
     */
    @Bean("managementService")
    @Primary
    @ConditionalOnMissingBean(name = "managementService")
    public Object emptyManagementService() {
        return new HashMap<>();
    }

    /**
     * 空的DynamicBpmnService实现
     */
    @Bean("dynamicBpmnService")
    @Primary
    @ConditionalOnMissingBean(name = "dynamicBpmnService")
    public Object emptyDynamicBpmnService() {
        return new HashMap<>();
    }

    /**
     * 空的ProcessMigrationService实现
     */
    @Bean("processMigrationService")
    @Primary
    @ConditionalOnMissingBean(name = "processMigrationService")
    public Object emptyProcessMigrationService() {
        return new HashMap<>();
    }
} 