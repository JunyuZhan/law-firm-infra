package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.service.OpenAPIService;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringDoc配置
 * 此配置类已禁用，使用Knife4j提供的API文档能力
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "springdoc", name = "api-docs.enabled", havingValue = "true", matchIfMissing = false)
public class SpringDocConfig {

    /**
     * 主API文档组
     */
    @Bean
    public GroupedOpenApi mainApi() {
        log.info("配置API文档分组");
        return GroupedOpenApi.builder()
                .group("main-api")
                .pathsToMatch("/api/**")
                .packagesToScan("com.lawfirm.api.controller")
                .build();
    }
    
    /**
     * 自定义API文档信息
     */
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.getInfo()
                .description("律师事务所管理系统API文档")
                .version("1.0.0");
    }
    
    /**
     * 主动修改日志级别，避免过多的警告日志
     */
    @Bean
    @Primary
    public SpringDocConfigProperties springDocConfigProperties() {
        SpringDocConfigProperties properties = new SpringDocConfigProperties();
        properties.setShowActuator(false);
        properties.setApiDocs(new SpringDocConfigProperties.ApiDocs());
        properties.getApiDocs().setEnabled(true);
        properties.getApiDocs().setPath("/v3/api-docs");
        
        log.info("自定义SpringDoc配置已加载");
        return properties;
    }
} 