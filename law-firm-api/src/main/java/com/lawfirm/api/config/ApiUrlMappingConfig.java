package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.extern.slf4j.Slf4j;

/**
 * API URL映射配置
 * 专门处理API文档的URL映射
 */
@Slf4j
@Configuration
public class ApiUrlMappingConfig implements WebMvcConfigurer {
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 添加API文档相关的视图控制器映射
     * 确保根路径映射到API文档，简化访问
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API文档URL映射, 上下文路径: {}", contextPath);
        
        // 配置根路径到文档的转发
        registry.addViewController("/").setViewName("forward:/doc.html");
        
        // 确保knife4j文档可访问
        registry.addViewController("/doc").setViewName("forward:/doc.html");
        
        // 确保swagger-ui可访问
        registry.addViewController("/swagger").setViewName("forward:/swagger-ui.html");
    }
} 