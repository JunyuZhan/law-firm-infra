package com.lawfirm.common.web.config;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Web安全配置
 * 
 * 提供通用Web安全功能，如XSS过滤
 * 注意：此配置优先级低于专用安全模块配置（如auth模块）
 */
@Configuration("commonWebSecurityConfig")
@Order(100)  // 优先级低于auth模块的安全配置
public class SecurityConfig {

    /**
     * XSS过滤器
     * 当没有其他同类Bean时才创建
     */
    @Bean("commonXssFilter")
    @ConditionalOnMissingBean
    public XssFilter commonXssFilter() {
        return new XssFilter();
    }
} 