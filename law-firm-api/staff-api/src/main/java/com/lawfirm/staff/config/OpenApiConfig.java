package com.lawfirm.staff.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI文档配置
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所管理系统 - 员工端API")
                .description("提供律师、文员、财务等相关功能接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("lawfirm")
                    .email("support@lawfirm.com")
                    .url("https://www.lawfirm.com")))
            .components(new Components()
                .addSecuritySchemes("Bearer",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList("Bearer"));
    }

    @Bean
    public GroupedOpenApi lawyerApi() {
        return GroupedOpenApi.builder()
            .group("1.律师接口")
            .pathsToMatch("/lawyer/**")
            .packagesToScan("com.lawfirm.staff.controller.lawyer")
            .build();
    }

    @Bean
    public GroupedOpenApi clerkApi() {
        return GroupedOpenApi.builder()
            .group("2.文员接口")
            .pathsToMatch("/clerk/**")
            .packagesToScan("com.lawfirm.staff.controller.clerk")
            .build();
    }

    @Bean
    public GroupedOpenApi financeApi() {
        return GroupedOpenApi.builder()
            .group("3.财务接口")
            .pathsToMatch("/finance/**")
            .packagesToScan("com.lawfirm.staff.controller.finance")
            .build();
    }
} 