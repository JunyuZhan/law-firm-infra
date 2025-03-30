package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.extern.slf4j.Slf4j;

/**
 * API URL映射配置
 * 提供URL映射转发，确保各种路径格式都能正确访问API
 */
@Slf4j
@Configuration
public class ApiUrlMappingConfig implements WebMvcConfigurer {
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API URL映射, context-path: {}", contextPath);
        
        String prefix = "";
        if (contextPath != null && !contextPath.isEmpty() && !"/".equals(contextPath)) {
            if (contextPath.startsWith("/")) {
                prefix = contextPath.substring(1);
            } else {
                prefix = contextPath;
            }
        }
        
        // 认证相关
        registry.addViewController("/auth/login").setViewName("forward:/login");
        registry.addViewController("/api/auth/login").setViewName("forward:/login");
        registry.addViewController("/auth/refresh").setViewName("forward:/refreshToken");
        registry.addViewController("/api/auth/refresh").setViewName("forward:/refreshToken");
        
        // 用户信息相关
        registry.addViewController("/users/info").setViewName("forward:/getUserInfo");
        registry.addViewController("/api/users/info").setViewName("forward:/getUserInfo");
        registry.addViewController("/auth/logout").setViewName("forward:/logout");
        registry.addViewController("/api/auth/logout").setViewName("forward:/logout");
        
        // 菜单相关
        registry.addViewController("/permissions/user/menus").setViewName("forward:/getMenuList");
        registry.addViewController("/api/permissions/user/menus").setViewName("forward:/getMenuList");
        registry.addViewController("/api/menu/list").setViewName("forward:/getMenuList");
        
        // 权限相关
        registry.addViewController("/api/permissions").setViewName("forward:/getPermCode");
        
        log.info("API URL映射配置完成");
    }
} 