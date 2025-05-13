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
import org.springframework.beans.factory.annotation.Qualifier;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Bean(name = "lawfirmMainApi")
    public GroupedOpenApi mainApi() {
        log.info("配置API文档分组");
        return GroupedOpenApi.builder()
                .group("main-api")
                .pathsToMatch("/api/**")
                .packagesToScan("com.lawfirm.api.controller")
                .build();
    }
    
    /**
     * 自定义API文档信息并设置字符编码
     */
    @Bean(name = "lawfirmOpenApiCustomizer")
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // 设置基本信息
            openApi.getInfo()
                    .description("律师事务所管理系统API文档")
                    .version("1.0.0");
                    
            // 确保所有响应内容类型包含UTF-8字符集
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    operation.getResponses().values().forEach(response -> {
                        if (response.getContent() != null) {
                            // 如果包含application/json，添加明确指定charset的版本
                            if (response.getContent().containsKey("application/json")) {
                                response.getContent().put("application/json;charset=UTF-8", 
                                    response.getContent().get("application/json"));
                            }
                        }
                    });
                });
            });
            
            log.info("OpenAPI文档UTF-8编码已设置");
        };
    }
    
    /**
     * 主动修改日志级别，避免过多的警告日志
     */
    @Bean(name = "lawfirmSpringDocConfigProperties")
    @Primary
    public SpringDocConfigProperties springDocConfigProperties(
            @Qualifier("primaryObjectMapper") ObjectMapper objectMapper) {
        SpringDocConfigProperties properties = new SpringDocConfigProperties();
        properties.setShowActuator(false);
        properties.setApiDocs(new SpringDocConfigProperties.ApiDocs());
        properties.getApiDocs().setEnabled(true);
        properties.getApiDocs().setPath("/v3/api-docs");
        
        log.info("自定义SpringDoc配置已加载，使用统一的primaryObjectMapper");
        return properties;
    }
} 