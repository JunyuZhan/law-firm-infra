package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * API文档统一配置类
 * 整合SpringDoc和Knife4j的文档配置，避免冲突
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "springdoc", name = "api-docs.enabled", havingValue = "true", matchIfMissing = false)
public class ApiDocConfiguration {

    @Value("${spring.application.name:律师事务所管理系统}")
    private String applicationName;
    
    @Value("${app.version:1.0.0}")
    private String applicationVersion;
    
    /**
     * 配置全局OpenAPI对象，提供统一的文档信息
     */
    @Bean(name = "lawfirmOpenAPI")
    public OpenAPI openAPI() {
        log.info("初始化全局API文档配置");
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " API文档")
                        .version(applicationVersion)
                        .description("律师事务所管理系统接口文档")
                        .contact(new Contact()
                                .name("律师事务所开发团队")
                                .email("dev@lawfirm.com")
                                .url("https://lawfirm.com")))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
    
    /**
     * 主API文档分组 - 简化版本
     */
    @Bean(name = "lawfirmMainApi")
    public GroupedOpenApi mainApi() {
        log.info("配置简化的API文档主分组");
        return GroupedOpenApi.builder()
                .group("核心API")
                // 只使用路径匹配，不使用包扫描
                .pathsToMatch("/api/**", "/v3/api-docs/**", "/swagger-ui/**")
                .build();
    }
    
    /**
     * 认证API文档分组 - 简化版本
     */
    @Bean(name = "lawfirmAuthApi")
    public GroupedOpenApi authApi() {
        log.info("配置简化的认证API文档分组");
        return GroupedOpenApi.builder()
                .group("认证授权")
                .pathsToMatch("/auth/**")
                .build();
    }
    
    /**
     * 自定义API文档配置
     */
    @Bean(name = "lawfirmOpenApiCustomizer")
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            log.info("应用API文档自定义配置");
            
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
        };
    }
    
    /**
     * 更明确的SpringDoc配置属性
     * 避免与其他组件冲突
     */
    @Bean
    @Primary
    public SpringDocConfigProperties springDocConfigProperties() {
        log.info("配置SpringDoc属性，使用默认配置");
        try {
            SpringDocConfigProperties properties = new SpringDocConfigProperties();
            properties.setShowActuator(false);
            properties.setApiDocs(new SpringDocConfigProperties.ApiDocs());
            properties.getApiDocs().setEnabled(true);
            properties.getApiDocs().setPath("/v3/api-docs");
            
            // 设置默认的媒体类型
            properties.setDefaultProducesMediaType("application/json;charset=UTF-8");
            properties.setDefaultConsumesMediaType("application/json;charset=UTF-8");
            
            // 禁用文档缓存
            properties.setCache(new SpringDocConfigProperties.Cache());
            properties.getCache().setDisabled(true);
            
            return properties;
        } catch (Exception e) {
            log.error("配置SpringDoc属性时发生异常", e);
            SpringDocConfigProperties fallback = new SpringDocConfigProperties();
            fallback.setApiDocs(new SpringDocConfigProperties.ApiDocs());
            fallback.getApiDocs().setEnabled(true);
            fallback.getApiDocs().setPath("/v3/api-docs");
            return fallback;
        }
    }
    
    /**
     * 提供一个简化的OpenAPI配置，避免扫描问题
     */
    @Bean(name = "simpleOpenAPI")
    public OpenAPI simpleOpenAPI() {
        log.info("初始化简化的API文档配置");
        return new OpenAPI()
                .info(new Info()
                        .title("律师事务所管理系统")
                        .version("1.0.0")
                        .description("律师事务所管理系统API文档"))
                .components(new Components());
    }
} 