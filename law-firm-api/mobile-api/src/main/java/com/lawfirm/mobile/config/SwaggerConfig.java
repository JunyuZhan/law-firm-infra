package com.lawfirm.mobile.config;

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
    public OpenAPI mobileOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律所管理系统 Mobile API")
                        .description("律所管理系统移动端API文档")
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
    public GroupedOpenApi caseApi() {
        return GroupedOpenApi.builder()
                .group("1.案件管理")
                .pathsToMatch("/case/**")
                .build();
    }

    @Bean
    public GroupedOpenApi scheduleApi() {
        return GroupedOpenApi.builder()
                .group("2.日程管理")
                .pathsToMatch("/schedule/**")
                .build();
    }

    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("3.消息通知")
                .pathsToMatch("/message/**")
                .build();
    }

    @Bean
    public GroupedOpenApi workApi() {
        return GroupedOpenApi.builder()
                .group("4.移动办公")
                .pathsToMatch("/work/**")
                .build();
    }
} 