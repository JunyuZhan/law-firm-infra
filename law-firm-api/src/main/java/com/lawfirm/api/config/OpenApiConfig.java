package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

/**
 * OpenAPI配置
 * 设置API文档信息和全局安全配置
 */
@Configuration
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {

    @Value("${springdoc.version:1.0.0}")
    private String version;

    /**
     * 配置OpenAPI文档信息
     */
    @Bean
    @Primary
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律所管理系统API文档")
                        .description("提供律所管理系统各模块的API接口文档")
                        .version(version)
                        .contact(new Contact()
                                .name("管理员")
                                .email("admin@lawfirm.com")
                                .url("https://www.lawfirm.com"))
                        .license(new License()
                                .name("Private License")
                                .url("https://www.lawfirm.com/license")))
                .externalDocs(new ExternalDocumentation()
                        .description("API使用说明")
                        .url("https://www.lawfirm.com/api-guide"))
                // 添加全局安全配置
                .components(new Components()
                        .addSecuritySchemes("Bearer Token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Token", Collections.emptyList()));
    }

    /**
     * 配置API文档分组 - 系统管理API
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .displayName("系统管理API")
                .pathsToMatch("/admin/**", "/system/**")
                .build();
    }

    /**
     * 配置API文档分组 - 业务模块API
     */
    @Bean
    public GroupedOpenApi businessApi() {
        return GroupedOpenApi.builder()
                .group("business-api")
                .displayName("业务模块API")
                .pathsToMatch("/api/**", "/case/**", "/task/**", "/client/**")
                .build();
    }

    /**
     * 配置API文档分组 - 公共API
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .displayName("公共API")
                .pathsToMatch("/", "/auth/**", "/public/**")
                .build();
    }
} 