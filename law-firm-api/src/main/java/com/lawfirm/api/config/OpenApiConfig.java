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
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
                    .email("support@lawfirm.com"))
                .license(new License()
                    .name("Private License")))
            .servers(List.of(
                new Server()
                    .url(contextPath)
                    .description("默认服务器")
            ));
    }
    
    /**
     * 定义API分组
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
     * 添加资源处理器，确保静态资源和文档相关资源可以被正确访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Knife4j静态资源
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        // Swagger相关资源
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
        
        // WebJars资源
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 