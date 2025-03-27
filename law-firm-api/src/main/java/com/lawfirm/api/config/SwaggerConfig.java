package com.lawfirm.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger API文档配置
 */
@Configuration
public class SwaggerConfig {

    @Value("${app.name:法律事务所管理系统}")
    private String appName;
    
    @Value("${app.version:1.0.0}")
    private String appVersion;
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName + " API文档")
                        .description("法律事务所管理系统API接口文档")
                        .version(appVersion)
                        .contact(new Contact()
                                .name("开发团队")
                                .email("dev@lawfirm.com")))
                .servers(List.of(
                        new Server().url("/api").description("API服务器")
                ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/api/admin/**")
                .build();
    }
} 