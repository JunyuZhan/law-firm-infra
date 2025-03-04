package com.lawfirm.staff.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置
 *
 * @author JunyuZhan
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI staffOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律所管理系统 Staff API")
                        .description("律所管理系统员工端API文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("weidi")
                                .email("weidi@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Token认证")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1.公共接口")
                .pathsToMatch("/common/**")
                .build();
    }

    @Bean
    public GroupedOpenApi lawyerApi() {
        return GroupedOpenApi.builder()
                .group("2.律师接口")
                .pathsToMatch("/lawyer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi clerkApi() {
        return GroupedOpenApi.builder()
                .group("3.文员接口")
                .pathsToMatch("/clerk/**")
                .build();
    }

    @Bean
    public GroupedOpenApi financeApi() {
        return GroupedOpenApi.builder()
                .group("4.财务接口")
                .pathsToMatch("/finance/**")
                .build();
    }
} 
