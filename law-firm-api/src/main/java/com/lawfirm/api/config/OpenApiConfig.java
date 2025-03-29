package com.lawfirm.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.util.List;
import java.util.ArrayList;

/**
 * API文档配置类
 * 统一使用Knife4j作为API文档系统
 */
@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 定义OpenAPI基本信息
     */
    @Bean
    @Primary
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所管理系统API")
                .version("1.0.0")
                .description("律师事务所管理系统API文档")
                .contact(new Contact()
                    .name("詹俊宇")
                    .email("junyuzhan@Outlook.com"))
                .license(new License()
                    .name("Private License")))
            .servers(List.of(
                new Server()
                    .url(contextPath)
                    .description("默认服务器")
            ))
            // 添加JWT认证配置
            .components(new Components()
                .addSecuritySchemes("Authorization", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")))
            .addSecurityItem(new SecurityRequirement().addList("Authorization"));
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
                .pathsToMatch("/api/client/**")
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
                .pathsToMatch("/api/cases/**")
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
                .pathsToMatch("/api/contract/**")
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
                .pathsToMatch("/api/knowledge/**")
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
                .pathsToMatch("/api/document/**")
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
                .pathsToMatch("/api/finance/**")
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
                .pathsToMatch("/api/personnel/**")
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
                .pathsToMatch("/api/system/**")
                .packagesToScan("com.lawfirm.system.controller")
                .build();
    }
    
    /**
     * 添加资源处理器，确保静态资源和文档相关资源可以被正确访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = contextPath;
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        
        // 处理不带context-path的路径
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        // 处理带context-path的路径
        if (!"/".equals(path)) {
            registry.addResourceHandler(path + "/doc.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler(path + "/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
            registry.addResourceHandler(path + "/swagger-resources/**")
                    .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
            registry.addResourceHandler(path + "/swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
            registry.addResourceHandler(path + "/v3/api-docs/**")
                    .addResourceLocations("classpath:/META-INF/resources/");
        }
    }
    
    /**
     * 添加视图控制器
     * 为Knife4j文档页面配置路径映射
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 简化访问路径
        registry.addViewController("/doc")
                .setViewName("redirect:/doc.html");
        registry.addViewController("/swagger")
                .setViewName("redirect:/swagger-ui/index.html");
        
        if (!"/".equals(contextPath)) {
            registry.addViewController(contextPath + "/doc")
                    .setViewName("redirect:" + contextPath + "/doc.html");
            registry.addViewController(contextPath + "/swagger")
                    .setViewName("redirect:" + contextPath + "/swagger-ui/index.html");
        }
    }
} 