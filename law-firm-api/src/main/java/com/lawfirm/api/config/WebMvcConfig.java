package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * Web MVC配置类 - 已禁用，避免与WebMvcCompatibilityConfig冲突
 * 所有API文档相关的配置已移至WebMvcCompatibilityConfig
 */
@Slf4j
@Configuration("webMvcConfig")
public class WebMvcConfig implements WebMvcConfigurer {
    
    // 不提供任何配置，避免冲突
    
} 