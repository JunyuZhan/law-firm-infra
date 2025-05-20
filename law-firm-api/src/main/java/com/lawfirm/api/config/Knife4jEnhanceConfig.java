package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 已废弃：在使用SpringDoc 3.x时，此配置会与OpenApiConfig冲突
 * 改为使用application.yml中的knife4j配置项
 * 
 * 此类仅保留结构，不再提供任何Bean实现
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "false", matchIfMissing = false)
public class Knife4jEnhanceConfig {

    @Value("${spring.application.name:法律事务管理系统}")
    private String applicationName;
} 