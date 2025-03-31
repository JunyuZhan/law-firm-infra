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
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * OpenAPI文档配置
 * <p>
 * 确保API文档相关的路径可以无需认证访问，优先级高于其他安全配置
 * </p>
 */
@Slf4j
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    /**
     * 创建OpenAPI基础信息 - 确保最高优先级
     */
    @Bean("apiOpenAPI")
    @Primary
    public OpenAPI apiOpenAPI() {
        log.info("初始化OpenAPI基础信息配置，上下文路径: {}", contextPath);
        
        // 规范化上下文路径，确保路径前有斜杠
        String normalizedContextPath = contextPath;
        if (!normalizedContextPath.startsWith("/")) {
            normalizedContextPath = "/" + normalizedContextPath;
        }
        
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所管理系统API")
                .description("提供律师事务所业务管理所需的各种API接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Law Firm Dev Team")
                    .email("dev@lawfirm.com")
                    .url("https://lawfirm.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .servers(List.of(
                new Server()
                    .url(normalizedContextPath)
                    .description("API服务器")
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
    @Bean("systemApiGroup")
    public GroupedOpenApi systemApiGroup() {
        return GroupedOpenApi.builder()
                .group("system")
                .displayName("系统接口")
                .pathsToMatch("/auth/**", "/login", "/logout", "/refreshToken", 
                             "/user/**", "/system/**", "/menu/**", "/role/**", 
                             "/dept/**", "/dict/**", "/config/**")
                .build();
    }

    /**
     * 业务API分组
     */
    @Bean("businessApiGroup")
    public GroupedOpenApi businessApiGroup() {
        return GroupedOpenApi.builder()
                .group("business")
                .displayName("业务接口")
                .pathsToMatch("/case/**", "/client/**", "/contract/**", 
                             "/document/**", "/knowledge/**")
                .build();
    }
} 