package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * API应用配置类
 * 集中管理所有模块配置和系统配置
 */
@Configuration
@PropertySource({
    "classpath:application.yml",
    "classpath:application-${spring.profiles.active:dev}.yml"
})
@ImportAutoConfiguration({
    // 基础设施层自动配置
    com.lawfirm.common.core.config.CommonAutoConfiguration.class,
    com.lawfirm.common.web.config.WebAutoConfiguration.class,
    com.lawfirm.common.data.config.DataAutoConfiguration.class,
    com.lawfirm.common.cache.config.CacheAutoConfiguration.class,
    
    // 核心层自动配置
    com.lawfirm.core.workflow.config.WorkflowAutoConfiguration.class,
    com.lawfirm.core.search.config.SearchAutoConfiguration.class,
    
    // 业务层自动配置
    com.lawfirm.auth.config.AuthAutoConfiguration.class
})
public class ApiApplicationConfig {
    // 配置类，不需要实现方法
} 