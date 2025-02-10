package com.lawfirm.mini.config;

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
 * @author weidi
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI miniOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律所管理系统 Mini API")
                        .description("律所管理系统小程序API文档")
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
    public GroupedOpenApi clientApi() {
        return GroupedOpenApi.builder()
                .group("1.客户服务")
                .pathsToMatch("/client/**")
                .build();
    }

    @Bean
    public GroupedOpenApi caseProgressApi() {
        return GroupedOpenApi.builder()
                .group("2.案件进度")
                .pathsToMatch("/case-progress/**")
                .build();
    }

    @Bean
    public GroupedOpenApi consultApi() {
        return GroupedOpenApi.builder()
                .group("3.在线咨询")
                .pathsToMatch("/consult/**")
                .build();
    }

    @Bean
    public GroupedOpenApi appointmentApi() {
        return GroupedOpenApi.builder()
                .group("4.预约服务")
                .pathsToMatch("/appointment/**")
                .build();
    }
} 