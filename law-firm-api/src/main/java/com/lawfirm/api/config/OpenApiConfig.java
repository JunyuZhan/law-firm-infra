package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI配置类
 * 用于配置Swagger API文档
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:law-firm-api}")
    private String applicationName;

    @Value("${law-firm.security.jwt.token-header:Authorization}")
    private String tokenHeader;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " API文档")
                        .description("律所管理系统API文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("律所管理系统开发团队")
                                .email("support@lawfirm.com")
                                .url("https://www.lawfirm.com"))
                        .license(new License()
                                .name("Private License")
                                .url("https://www.lawfirm.com/license")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name(tokenHeader)))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
    
    /**
     * 自定义OpenAPI定制器，用于优化文档生成
     */
    @Bean
    @SuppressWarnings("rawtypes") // 抑制原始类型警告
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // 简化文档内容，移除过于复杂的模型
            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                // 移除特定过于复杂的模型，避免循环依赖
                openApi.getComponents().getSchemas().entrySet().removeIf(entry -> 
                    entry.getKey().contains("Monitor") || 
                    entry.getKey().contains("Audit") ||
                    entry.getKey().contains("SysLog"));
            }
        };
    }

    @Bean
    public GroupedOpenApi allApisGroup() {
        return GroupedOpenApi.builder()
                .group("所有API")
                .pathsToMatch("/**")
                .packagesToScan("com.lawfirm")
                .pathsToExclude("/actuator/**", "/api/system/monitor/**")
                .build();
    }

    @Bean
    public GroupedOpenApi systemApiGroup() {
        return GroupedOpenApi.builder()
                .group("系统管理")
                .pathsToMatch("/api/system/**")
                .pathsToExclude("/api/system/monitor/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApiGroup() {
        return GroupedOpenApi.builder()
                .group("认证授权")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi clientApiGroup() {
        return GroupedOpenApi.builder()
                .group("客户管理")
                .pathsToMatch("/api/client/**")
                .build();
    }

    @Bean
    public GroupedOpenApi caseApiGroup() {
        return GroupedOpenApi.builder()
                .group("案件管理")
                .pathsToMatch("/api/case/**")
                .build();
    }

    @Bean
    public GroupedOpenApi taskApiGroup() {
        return GroupedOpenApi.builder()
                .group("任务管理")
                .pathsToMatch("/api/task/**")
                .build();
    }

    @Bean
    public GroupedOpenApi scheduleApiGroup() {
        return GroupedOpenApi.builder()
                .group("日程管理")
                .pathsToMatch("/api/schedule/**")
                .build();
    }

    @Bean
    public GroupedOpenApi knowledgeApiGroup() {
        return GroupedOpenApi.builder()
                .group("知识管理")
                .pathsToMatch("/api/knowledge/**")
                .build();
    }

    @Bean
    public GroupedOpenApi documentApiGroup() {
        return GroupedOpenApi.builder()
                .group("文档管理")
                .pathsToMatch("/api/document/**")
                .build();
    }

    @Bean
    public GroupedOpenApi personnelApiGroup() {
        return GroupedOpenApi.builder()
                .group("人员管理")
                .pathsToMatch("/api/personnel/**")
                .build();
    }

    @Bean
    public GroupedOpenApi financeApiGroup() {
        return GroupedOpenApi.builder()
                .group("财务管理")
                .pathsToMatch("/api/finance/**")
                .build();
    }

    @Bean
    public GroupedOpenApi archiveApiGroup() {
        return GroupedOpenApi.builder()
                .group("档案管理")
                .pathsToMatch("/api/archive/**")
                .build();
    }
} 