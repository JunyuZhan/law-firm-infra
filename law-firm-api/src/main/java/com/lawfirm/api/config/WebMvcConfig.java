package com.lawfirm.api.config;

import com.lawfirm.api.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 * 处理拦截器、资源处理等Web相关配置
 * 注意：跨域配置已由common-web模块的WebConfig类统一提供
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    /**
     * 配置拦截器
     * 在开发环境中可通过配置禁用
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只添加基础请求拦截器，安全拦截器由专门的安全配置处理
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/favicon.ico", "/health", "/version");
    }

    /**
     * 配置静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
} 