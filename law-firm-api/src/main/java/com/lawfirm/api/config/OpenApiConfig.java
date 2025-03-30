package com.lawfirm.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * API文档配置类
 * 统一使用Knife4j作为API文档系统
 */
@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Value("${app.version:1.0.0}")
    private String appVersion;
    
    @Value("${app.name:律师事务所管理系统}")
    private String appName;
    
    @Value("${app.description:律师事务所管理系统API服务}")
    private String appDescription;

    /**
     * 定义OpenAPI基本信息
     */
    @Bean
    @Primary
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(appName + "API文档")
                .version(appVersion)
                .description(appDescription + "\n\n提供律师事务所各模块的API接口")
                .contact(new Contact()
                    .name("詹俊宇")
                    .email("junyuzhan@Outlook.com")
                    .url("https://github.com/junyuzhan"))
                .license(new License()
                    .name("Private License")
                    .url("https://license.example.com")))
            .externalDocs(new ExternalDocumentation()
                .description("系统说明文档")
                .url("https://example.com/docs"))
            .servers(List.of(
                new Server()
                    .url(contextPath)
                    .description("默认服务器")
            ))
            // 添加常用标签
            .tags(Arrays.asList(
                new Tag().name("认证").description("用户认证相关接口"),
                new Tag().name("客户管理").description("客户信息管理相关接口"),
                new Tag().name("案件管理").description("案件信息管理相关接口"),
                new Tag().name("合同管理").description("合同信息管理相关接口"),
                new Tag().name("文档管理").description("文档信息管理相关接口"),
                new Tag().name("知识管理").description("知识库管理相关接口"),
                new Tag().name("财务管理").description("财务信息管理相关接口"),
                new Tag().name("人事管理").description("人事信息管理相关接口"),
                new Tag().name("系统管理").description("系统配置管理相关接口")
            ))
            // 添加JWT认证配置
            .components(new Components()
                .addSecuritySchemes("Authorization", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")
                    .description("JWT认证token，格式：Bearer {token}")))
            .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }
    
    /**
     * 自定义OpenAPI，设置特定的描述和扩展属性
     */
    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    // 为每个操作添加通用响应描述
                    if (operation.getResponses().get("200") != null) {
                        operation.getResponses().get("200").setDescription("操作成功");
                    }
                    if (operation.getResponses().get("401") != null) {
                        operation.getResponses().get("401").setDescription("未授权，请先登录");
                    }
                    if (operation.getResponses().get("403") != null) {
                        operation.getResponses().get("403").setDescription("权限不足，无法访问");
                    }
                    if (operation.getResponses().get("500") != null) {
                        operation.getResponses().get("500").setDescription("服务器内部错误");
                    }
                });
    }
    
    /**
     * 定义API分组 - 所有接口
     */
    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("所有接口")
                .pathsToMatch("/**")
                .packagesToScan("com.lawfirm")
                .build();
    }
    
    /**
     * 定义API分组 - 客户管理
     */
    @Bean
    public GroupedOpenApi clientApis() {
        return GroupedOpenApi.builder()
                .group("客户管理")
                .pathsToMatch("/client/**")
                .packagesToScan("com.lawfirm.client.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 案件管理
     */
    @Bean
    public GroupedOpenApi caseApis() {
        return GroupedOpenApi.builder()
                .group("案件管理")
                .pathsToMatch("/cases/**")
                .packagesToScan("com.lawfirm.cases.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 合同管理
     */
    @Bean
    public GroupedOpenApi contractApis() {
        return GroupedOpenApi.builder()
                .group("合同管理")
                .pathsToMatch("/contract/**")
                .packagesToScan("com.lawfirm.contract.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 知识管理
     */
    @Bean
    public GroupedOpenApi knowledgeApis() {
        return GroupedOpenApi.builder()
                .group("知识管理")
                .pathsToMatch("/knowledge/**")
                .packagesToScan("com.lawfirm.knowledge.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 文档管理
     */
    @Bean
    public GroupedOpenApi documentApis() {
        return GroupedOpenApi.builder()
                .group("文档管理")
                .pathsToMatch("/document/**")
                .packagesToScan("com.lawfirm.document.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 财务管理
     */
    @Bean
    public GroupedOpenApi financeApis() {
        return GroupedOpenApi.builder()
                .group("财务管理")
                .pathsToMatch("/finance/**")
                .packagesToScan("com.lawfirm.finance.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 人事管理
     */
    @Bean
    public GroupedOpenApi personnelApis() {
        return GroupedOpenApi.builder()
                .group("人事管理")
                .pathsToMatch("/personnel/**")
                .packagesToScan("com.lawfirm.personnel.controller")
                .build();
    }
    
    /**
     * 定义API分组 - 系统管理
     */
    @Bean
    public GroupedOpenApi systemApis() {
        return GroupedOpenApi.builder()
                .group("系统管理")
                .pathsToMatch("/system/**")
                .packagesToScan("com.lawfirm.system.controller")
                .build();
    }
    
    /**
     * 添加资源处理器，确保静态资源和文档相关资源可以被正确访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 处理不带context-path的路径
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        // 处理带context-path的路径
        if (contextPath != null && !"/".equals(contextPath)) {
            registry.addResourceHandler(contextPath + "/doc.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler(contextPath + "/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
    
    /**
     * 添加视图控制器，提供文档入口
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/doc", "/doc.html");
        if (contextPath != null && !"/".equals(contextPath)) {
            registry.addRedirectViewController(contextPath + "/doc", contextPath + "/doc.html");
        }
    }
    
    /**
     * 配置内容协商，确保API文档始终以JSON格式返回
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    /**
     * 添加专门针对API文档的响应体处理
     */
    @Bean
    public OpenApiCustomizer apiDocContentOpenApiCustomizer() {
        return openApi -> {
            // 确保所有API文档响应格式正确
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    // 添加OpenAPI生产内容类型
                    if (operation.getResponses().get("200") != null) {
                        operation.getResponses().get("200").getContent()
                            .put(MediaType.APPLICATION_JSON_VALUE, 
                                new io.swagger.v3.oas.models.media.MediaType());
                    }
                });
            });
        };
    }
} 