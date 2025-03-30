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
     * 认证API分组
     */
    @Bean
    public GroupedOpenApi authApiGroup() {
        return GroupedOpenApi.builder()
                .group("auth")
                .displayName("认证相关接口")
                .pathsToMatch("/auth/**", "/login", "/logout", "/refreshToken", "/user/**")
                .packagesToScan("com.lawfirm.api.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 系统管理API分组
     */
    @Bean
    public GroupedOpenApi systemApiGroup() {
        return GroupedOpenApi.builder()
                .group("system")
                .displayName("系统管理接口")
                .pathsToMatch("/system/**", "/menu/**", "/role/**", "/dept/**", "/dict/**", "/config/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.system.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 案件管理API分组
     */
    @Bean
    public GroupedOpenApi caseApiGroup() {
        return GroupedOpenApi.builder()
                .group("case")
                .displayName("案件管理接口")
                .pathsToMatch("/case/**", "/cases/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.cases.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 客户管理API分组
     */
    @Bean
    public GroupedOpenApi clientApiGroup() {
        return GroupedOpenApi.builder()
                .group("client")
                .displayName("客户管理接口")
                .pathsToMatch("/client/**", "/clients/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.client.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 合同管理API分组
     */
    @Bean
    public GroupedOpenApi contractApiGroup() {
        return GroupedOpenApi.builder()
                .group("contract")
                .displayName("合同管理接口")
                .pathsToMatch("/contract/**", "/contracts/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.contract.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 文档管理API分组
     */
    @Bean
    public GroupedOpenApi documentApiGroup() {
        return GroupedOpenApi.builder()
                .group("document")
                .displayName("文档管理接口")
                .pathsToMatch("/document/**", "/documents/**", "/files/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.document.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }

    /**
     * 知识库API分组
     */
    @Bean
    public GroupedOpenApi knowledgeApiGroup() {
        return GroupedOpenApi.builder()
                .group("knowledge")
                .displayName("知识库管理接口")
                .pathsToMatch("/knowledge/**")
                .packagesToScan("com.lawfirm.api.controller", "com.lawfirm.knowledge.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }
    
    /**
     * 所有API分组
     */
    @Bean
    public GroupedOpenApi allApisGroup() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .displayName("所有接口")
                .pathsToMatch("/**")
                .packagesToScan("com.lawfirm.api.controller")
                .addOpenApiCustomizer(openApi -> openApi.openapi("3.0.1"))
                .build();
    }
} 