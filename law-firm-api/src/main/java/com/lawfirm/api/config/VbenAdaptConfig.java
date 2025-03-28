package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Vue-Vben-Admin适配配置
 * 用于添加URL映射，确保原有API路径仍然有效
 */
@Configuration
public class VbenAdaptConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 认证相关
        registry.addViewController("/auth/login").setViewName("forward:/api/login");
        registry.addViewController("/api/auth/login").setViewName("forward:/api/login");
        registry.addViewController("/auth/refresh").setViewName("forward:/api/refreshToken");
        
        // 用户信息相关
        registry.addViewController("/users/info").setViewName("forward:/api/getUserInfo");
        registry.addViewController("/auth/logout").setViewName("forward:/api/logout");
        
        // 菜单相关
        registry.addViewController("/permissions/user/menus").setViewName("forward:/api/getMenuList");
    }
} 