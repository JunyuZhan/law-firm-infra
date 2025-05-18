package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * API版本控制配置
 * 用于管理API版本
 */
@Configuration("apiVersionConfig")
public class ApiVersionConfig implements WebMvcConfigurer {

    /**
     * 当前API版本
     * 与BaseApiController.API_VERSION保持一致
     */
    public static final String CURRENT_API_VERSION = "v1";
    
    /**
     * API版本前缀
     * 与BaseApiController.API_VERSION_PREFIX保持一致
     */
    public static final String API_VERSION_PREFIX = "/api/" + CURRENT_API_VERSION;
    
    /**
     * API通用前缀
     */
    public static final String API_PREFIX = "/api";
    
    /**
     * 配置路径匹配
     * 使用PathPatternParser提高性能
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPatternParser(new PathPatternParser());
    }
    
    /**
     * 获取当前API版本
     */
    @Bean("apiVersion")
    public String apiVersion() {
        return CURRENT_API_VERSION;
    }
    
    /**
     * 获取API版本前缀
     */
    @Bean("apiVersionPrefix")
    public String apiVersionPrefix() {
        return API_VERSION_PREFIX;
    }
    
    /**
     * 获取API通用前缀
     */
    @Bean("apiPrefix")
    public String apiPrefix() {
        return API_PREFIX;
    }
} 