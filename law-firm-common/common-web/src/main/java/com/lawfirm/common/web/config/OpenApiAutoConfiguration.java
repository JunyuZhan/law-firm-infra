package com.lawfirm.common.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

/**
 * OpenAPI自动配置类
 * <p>
 * API文档功能已禁用，此配置类不会加载
 * </p>
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnClass(OpenAPI.class)
@ConditionalOnProperty(
    name = {"springdoc.api-docs.enabled", "knife4j.enable"}, 
    havingValue = "true", 
    matchIfMissing = false
)
public class OpenApiAutoConfiguration {

    @Value("${spring.application.name:律所管理系统}")
    private String applicationName;

    @Value("${app.version:1.0.0}")
    private String applicationVersion;

    /**
     * 配置OpenAPI基本信息
     * 仅当API文档功能明确启用且没有其他OpenAPI bean时生效
     */
    @Bean(name = "openAPI")
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI defaultOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.1")
                .info(new Info()
                        .title(applicationName + " API")
                        .version(applicationVersion)
                        .description("律所管理系统接口文档")
                        .contact(new Contact()
                                .name("律所管理团队")
                                .email("support@lawfirm.com")
                                .url("https://www.lawfirm.com"))
                        .license(new License()
                                .name("Private License")
                                .url("https://www.lawfirm.com/license")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

    /**
     * 配置默认API分组
     * 仅当API文档功能明确启用且没有其他GroupedOpenApi bean时生效
     */
    @Bean(name = "defaultGroupedOpenApi")
    @ConditionalOnMissingBean(name = "defaultGroupedOpenApi")
    public GroupedOpenApi defaultGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .build();
    }
    
    /**
     * 配置认证模块API分组
     * 仅当API文档功能明确启用才生效
     */
    @Bean(name = "authGroupedOpenApi")
    @ConditionalOnMissingBean(name = "authGroupedOpenApi")
    public GroupedOpenApi authGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("认证授权")
                .pathsToMatch("/auth/**")
                .build();
    }
} 