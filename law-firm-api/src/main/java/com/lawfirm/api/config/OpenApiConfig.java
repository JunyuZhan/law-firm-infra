package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OpenAPI配置类
 * 配置API文档生成
 * 
 * 使用SpringDoc 2.3.0 (适用于Spring Boot 3.x)
 * 分组配置通过application.yml中的springdoc.group-configs属性实现
 */
@Configuration("apiDocConfig")
@Profile({"dev", "test"}) // 仅在开发和测试环境默认启用
public class OpenApiConfig {

    @Value("${spring.application.name:法律事务管理系统}")
    private String applicationName;
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;
    
    @Value("${server.port:8080}")
    private Integer serverPort;
    
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    
    private final Environment environment;
    
    public OpenApiConfig(Environment environment) {
        this.environment = environment;
    }
    
    /**
     * 配置OpenAPI信息
     * 在SpringDoc 2.x中，分组通过application.yml中的配置项实现
     */
    @Bean(name = "openAPI")
    @Primary
    public OpenAPI lawFirmOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " API文档")
                        .description("法律事务管理系统API接口文档，提供系统所有接口的详细说明和调试功能")
                        .version(ApiVersionConfig.CURRENT_API_VERSION)
                        .contact(new Contact()
                                .name("技术支持团队")
                                .email("support@lawfirm.com")
                                .url("https://www.lawfirm.com"))
                        .license(new License()
                                .name("商业许可")
                                .url("https://www.lawfirm.com/license"))
                        .termsOfService("https://www.lawfirm.com/terms"))
                .externalDocs(new ExternalDocumentation()
                        .description("系统说明文档")
                        .url("https://docs.lawfirm.com"))
                .servers(getServers())
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("输入JWT令牌，格式为: Bearer {token}")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
    
    /**
     * 配置API服务器列表
     */
    private List<Server> getServers() {
        List<Server> servers = new ArrayList<>();
        
        // 确保上下文路径正确格式化
        String formattedContextPath = contextPath;
        if (formattedContextPath == null) {
            formattedContextPath = "";
        }
        if (!formattedContextPath.isEmpty() && !formattedContextPath.startsWith("/")) {
            formattedContextPath = "/" + formattedContextPath;
        }
        
        // 本地开发环境
        String localUrl = "http://localhost:" + serverPort + formattedContextPath;
        servers.add(new Server().url(localUrl).description("本地环境"));
        
        // 添加其他环境服务器
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            
            // 测试环境
            if ("test".equals(profile)) {
                servers.add(new Server().url("https://test-api.lawfirm.com").description("测试环境"));
            }
            
            // 预发布环境
            if ("staging".equals(profile)) {
                servers.add(new Server().url("https://staging-api.lawfirm.com").description("预发布环境"));
            }
        }
        
        return servers;
    }
} 