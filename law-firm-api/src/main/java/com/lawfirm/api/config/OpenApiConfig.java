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

import org.springdoc.core.models.GroupedOpenApi;

/**
 * OpenAPI配置类
 * 配置API文档生成
 * 
 * 使用SpringDoc 2.3.0 (适用于Spring Boot 3.x)
 * 直接使用代码方式配置分组，而不是通过application.yml
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
     * 客户管理分组
     */
    @Bean
    public GroupedOpenApi clientGroupApi() {
        return GroupedOpenApi.builder()
                .group("客户管理")
                .packagesToScan("com.lawfirm.client")
                .pathsToMatch("/api/v1/clients/**", "/api/v1/contacts/**", "/api/v1/client-**/**")
                .build();
    }

    /**
     * 案件管理分组
     */
    @Bean
    public GroupedOpenApi caseGroupApi() {
        return GroupedOpenApi.builder()
                .group("案件管理")
                .packagesToScan("com.lawfirm.cases")
                .pathsToMatch("/api/v1/cases/**", "/api/v1/case-**/**", "/api/v1/litigation/**", "/api/v1/arbitration/**")
                .build();
    }

    /**
     * 合同管理分组
     */
    @Bean
    public GroupedOpenApi contractGroupApi() {
        return GroupedOpenApi.builder()
                .group("合同管理")
                .packagesToScan("com.lawfirm.contract")
                .pathsToMatch("/api/v1/contracts/**", "/api/v1/contract-**/**")
                .build();
    }

    /**
     * 档案管理分组
     */
    @Bean
    public GroupedOpenApi archiveGroupApi() {
        return GroupedOpenApi.builder()
                .group("档案管理")
                .packagesToScan("com.lawfirm.archive")
                .pathsToMatch("/api/v1/archive/**", "/api/v1/archives/**", "/api/v1/archive-**/**")
                .build();
    }

    /**
     * 知识库分组
     */
    @Bean
    public GroupedOpenApi knowledgeGroupApi() {
        return GroupedOpenApi.builder()
                .group("知识库")
                .packagesToScan("com.lawfirm.knowledge")
                .pathsToMatch("/api/v1/knowledge/**", "/api/v1/knowledge-**/**")
                .build();
    }

    /**
     * 任务管理分组
     */
    @Bean
    public GroupedOpenApi taskGroupApi() {
        return GroupedOpenApi.builder()
                .group("任务管理")
                .packagesToScan("com.lawfirm.task")
                .pathsToMatch("/api/v1/tasks/**", "/api/v1/**-tasks/**", "/api/v1/work-task**/**")
                .build();
    }

    /**
     * 日程管理分组
     */
    @Bean
    public GroupedOpenApi scheduleGroupApi() {
        return GroupedOpenApi.builder()
                .group("日程管理")
                .packagesToScan("com.lawfirm.schedule")
                .pathsToMatch("/api/v1/schedules/**", "/api/v1/schedule-**/**", "/api/v1/calendar/**", "/api/v1/meeting-**/**")
                .build();
    }

    /**
     * 财务管理分组
     */
    @Bean
    public GroupedOpenApi financeGroupApi() {
        return GroupedOpenApi.builder()
                .group("财务管理")
                .packagesToScan("com.lawfirm.finance")
                .pathsToMatch("/api/v1/finance/**", "/api/v1/accounts/**", "/api/v1/expenses/**", 
                            "/api/v1/incomes/**", "/api/v1/invoices/**", "/api/v1/receivables/**", 
                            "/api/v1/transactions/**", "/api/v1/fees/**", "/api/v1/cost-**/**", 
                            "/api/v1/billing-**/**", "/api/v1/budget**/**", "/api/v1/payment-**/**",
                            "/api/v1/**-finance/**")
                .build();
    }

    /**
     * 人员管理分组
     */
    @Bean
    public GroupedOpenApi personnelGroupApi() {
        return GroupedOpenApi.builder()
                .group("人员管理")
                .packagesToScan("com.lawfirm.personnel")
                .pathsToMatch("/api/v1/personnel/**", "/api/v1/employees/**", "/api/v1/organizations/**", 
                           "/api/v1/positions/**", "/api/v1/teams/**")
                .build();
    }

    /**
     * 文档管理分组
     */
    @Bean
    public GroupedOpenApi documentGroupApi() {
        return GroupedOpenApi.builder()
                .group("文档管理")
                .packagesToScan("com.lawfirm.document")
                .pathsToMatch("/api/v1/documents/**", "/api/v1/document-**/**", "/api/v1/files/**", 
                           "/api/v1/templates/**", "/api/v1/preview/**")
                .build();
    }

    /**
     * 系统管理分组
     */
    @Bean
    public GroupedOpenApi systemGroupApi() {
        return GroupedOpenApi.builder()
                .group("系统管理")
                .packagesToScan("com.lawfirm.system")
                .pathsToMatch("/api/v1/system/**", "/api/v1/dict/**", "/api/v1/config/**", 
                           "/api/v1/monitor/**", "/api/v1/database-**/**", "/api/v1/upgrade/**")
                .build();
    }

    /**
     * 认证授权分组
     */
    @Bean
    public GroupedOpenApi authGroupApi() {
        return GroupedOpenApi.builder()
                .group("认证授权")
                .packagesToScan("com.lawfirm.auth")
                .pathsToMatch("/api/auth/**", "/api/v1/users/**", "/api/v1/roles/**", "/api/v1/permissions/**")
                .build();
    }

    /**
     * 分析报表分组
     */
    @Bean
    public GroupedOpenApi analysisGroupApi() {
        return GroupedOpenApi.builder()
                .group("分析报表")
                .packagesToScan("com.lawfirm.analysis")
                .pathsToMatch("/api/analysis/**", "/api/v1/analysis/**", "/api/v1/reports/**", 
                           "/api/v1/statistics/**", "/api/v1/dashboards/**")
                .build();
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