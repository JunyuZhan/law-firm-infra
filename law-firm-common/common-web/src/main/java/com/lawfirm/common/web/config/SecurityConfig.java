package com.lawfirm.common.web.config;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web安全配置
 */
@Configuration
public class SecurityConfig {

    /**
     * XSS过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public XssFilter xssFilter() {
        return new XssFilter();
    }
} 