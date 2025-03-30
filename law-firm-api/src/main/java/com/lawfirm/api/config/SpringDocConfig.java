package com.lawfirm.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Arrays;
import java.util.List;

/**
 * SpringDoc配置类
 * <p>配置API文档生成</p>
 */
@Configuration
@ConditionalOnProperty(name = "use.legacy.springdoc", havingValue = "true", matchIfMissing = false)
public class SpringDocConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * API信息配置
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所管理系统API")
                .description("提供律师事务所各模块的API接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("技术支持")
                    .email("support@lawfirm.com")
                    .url("https://www.lawfirm.com"))
                .license(new License()
                    .name("商业许可")
                    .url("https://www.lawfirm.com/license")))
            // 通过添加前缀为空的server配置，解决重复/api前缀问题
            .servers(List.of(
                new Server().url("").description("当前服务器"),
                new Server().url(contextPath).description("API路径")
            ));
    }
    
    /**
     * 权限与认证API分组
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("认证模块")
                .pathsToMatch("/auth/**", "/role/**", "/permission/**", "/user/**")
                .packagesToScan("com.lawfirm.auth.controller")
                .build();
    }
    
    /**
     * 人事管理API分组
     */
    @Bean
    public GroupedOpenApi personnelApi() {
        return GroupedOpenApi.builder()
                .group("人员模块")
                .pathsToMatch("/personnel/**")
                .packagesToScan("com.lawfirm.personnel.controller")
                .build();
    }
    
    /**
     * 客户管理API分组
     */
    @Bean
    public GroupedOpenApi clientApi() {
        return GroupedOpenApi.builder()
                .group("客户模块")
                .pathsToMatch("/client/**")
                .packagesToScan("com.lawfirm.client.controller")
                .build();
    }
    
    /**
     * 案件管理API分组
     */
    @Bean
    public GroupedOpenApi caseApi() {
        return GroupedOpenApi.builder()
                .group("案件模块")
                .pathsToMatch("/case/**", "/cases/**")
                .packagesToScan("com.lawfirm.cases.controller")
                .build();
    }
    
    /**
     * 合同管理API分组
     */
    @Bean
    public GroupedOpenApi contractApi() {
        return GroupedOpenApi.builder()
                .group("合同模块")
                .pathsToMatch("/contract/**")
                .packagesToScan("com.lawfirm.contract.controller")
                .build();
    }
    
    /**
     * 文档管理API分组
     */
    @Bean
    public GroupedOpenApi documentApi() {
        return GroupedOpenApi.builder()
                .group("文档模块")
                .pathsToMatch("/document/**")
                .packagesToScan("com.lawfirm.document.controller")
                .build();
    }
    
    /**
     * 知识管理API分组
     */
    @Bean
    public GroupedOpenApi knowledgeApi() {
        return GroupedOpenApi.builder()
                .group("知识模块")
                .pathsToMatch("/knowledge/**")
                .packagesToScan("com.lawfirm.knowledge.controller")
                .build();
    }
    
    /**
     * 财务管理API分组
     */
    @Bean
    public GroupedOpenApi financeApi() {
        return GroupedOpenApi.builder()
                .group("财务模块")
                .pathsToMatch("/finance/**")
                .packagesToScan("com.lawfirm.finance.controller")
                .build();
    }
    
    /**
     * 系统管理API分组
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
                .group("系统模块")
                .pathsToMatch("/system/**")
                .packagesToScan("com.lawfirm.system.controller")
                .build();
    }
    
    /**
     * API模块接口分组
     */
    @Bean
    public GroupedOpenApi apiModuleApi() {
        return GroupedOpenApi.builder()
                .group("通用接口")
                .pathsToMatch("/**")
                .packagesToScan("com.lawfirm.api.controller")
                .build();
    }
}