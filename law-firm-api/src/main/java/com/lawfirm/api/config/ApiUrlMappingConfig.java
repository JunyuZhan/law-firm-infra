package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API URL映射配置
 * 提供URL映射转发，确保各种路径格式都能正确访问API
 */
@Configuration
public class ApiUrlMappingConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 认证相关
        registry.addViewController("/auth/login").setViewName("forward:/api/login");
        registry.addViewController("/api/auth/login").setViewName("forward:/api/login");
        registry.addViewController("/auth/refresh").setViewName("forward:/api/refreshToken");
        registry.addViewController("/api/auth/refresh").setViewName("forward:/api/refreshToken");
        
        // 用户信息相关
        registry.addViewController("/users/info").setViewName("forward:/api/getUserInfo");
        registry.addViewController("/api/users/info").setViewName("forward:/api/getUserInfo");
        registry.addViewController("/auth/logout").setViewName("forward:/api/logout");
        registry.addViewController("/api/auth/logout").setViewName("forward:/api/logout");
        
        // 菜单相关
        registry.addViewController("/permissions/user/menus").setViewName("forward:/api/getMenuList");
        registry.addViewController("/api/permissions/user/menus").setViewName("forward:/api/getMenuList");
        registry.addViewController("/api/menu/list").setViewName("forward:/api/getMenuList");
        
        // 权限相关
        registry.addViewController("/api/permissions").setViewName("forward:/api/getPermCode");
    }
} 