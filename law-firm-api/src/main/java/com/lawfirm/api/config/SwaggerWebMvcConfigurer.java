package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * Swagger配置 - 补充视图配置
 * 主要负责API文档的视图跳转，核心功能已移至Knife4jConfiguration
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerWebMvcConfigurer implements WebMvcConfigurer {
    
    @Value("${spring.application.name:律师事务所管理系统}")
    private String applicationName;
    
    @Value("${knife4j.setting.language:zh_cn}")
    private String language;
    
    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerPath;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API文档页面跳转");
        
        // API文档入口跳转
        registry.addViewController("/")
                .setViewName("redirect:/doc.html");
                
        // 添加Swagger重定向
        registry.addViewController("/swagger")
                .setViewName("redirect:" + swaggerPath);
        
        // 添加Knife4j文档访问路径
        registry.addViewController("/api")
                .setViewName("redirect:/doc.html");
                
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
} 