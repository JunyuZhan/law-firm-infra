package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * OpenAPI文档配置
 * <p>
 * 配置API文档的展示信息、安全方案等
 * </p>
 */
@Slf4j
@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    /**
     * 创建OpenAPI基础信息
     */
    @Bean
    public OpenAPI lawFirmOpenAPI() {
        log.info("初始化OpenAPI基础信息配置");
        return new OpenAPI()
            // 手动设置openapi版本，解决格式问题
            .openapi("3.0.1")
            .info(new Info()
                .title("律师事务所管理系统API")
                .description("提供律师事务所业务管理所需的各种API接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Law Firm Dev Team")
                    .email("dev@lawfirm.com")
                    .url("https://lawfirm.com")))
            .servers(List.of(
                new Server()
                    .url(contextPath)
                    .description("默认服务器")
            ))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT Token认证")))
            .security(List.of(
                new SecurityRequirement().addList("bearerAuth")
            ));
    }

    /**
     * 认证和系统API分组
     */
    @Bean
    public GroupedOpenApi authAndSystemApiGroup() {
        return GroupedOpenApi.builder()
                .group("system")
                .displayName("系统接口")
                .pathsToMatch("/auth/**", "/login", "/logout", "/refreshToken", 
                             "/user/**", "/system/**", "/menu/**", "/role/**", 
                             "/dept/**", "/dict/**", "/config/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.system.controller", 
                               "com.lawfirm.auth.controller")
                .build();
    }

    /**
     * 业务API分组
     */
    @Bean
    public GroupedOpenApi businessApiGroup() {
        return GroupedOpenApi.builder()
                .group("business")
                .displayName("业务接口")
                .pathsToMatch("/case/**", "/client/**", "/contract/**", 
                             "/document/**", "/knowledge/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.cases.controller",
                               "com.lawfirm.client.controller", "com.lawfirm.contract.controller",
                               "com.lawfirm.document.controller", "com.lawfirm.knowledge.controller")
                .build();
    }
    
    /**
     * 所有API分组
     */
    @Bean
    public GroupedOpenApi allApisGroup() {
        return GroupedOpenApi.builder()
                .group("all")
                .displayName("所有接口")
                .pathsToMatch("/**")
                .build();
    }
} 