package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * API版本控制配置
 * 用于集中管理API版本和路径前缀
 */
@Configuration("apiVersionConfig")
public class ApiVersionConfig implements WebMvcConfigurer {

    /**
     * 当前API版本
     * 整个系统中API版本的唯一定义源
     */
    public static final String CURRENT_API_VERSION = "v1";
    
    /**
     * API基础路径前缀
     */
    public static final String API_PREFIX = "/api";
    
    /**
     * 版本化的API路径前缀
     */
    public static final String API_VERSION_PREFIX = API_PREFIX + "/" + CURRENT_API_VERSION;
    
    /**
     * 配置路径匹配
     * 使用PathPatternParser提高性能
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPatternParser(new PathPatternParser());
    }
} 