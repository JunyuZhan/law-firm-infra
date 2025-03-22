package com.lawfirm.api.config;

import com.lawfirm.common.web.config.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.stereotype.Component;

/**
 * API层Web配置扩展
 */
@Configuration
@Component("apiWebConfig")
public class ApiWebConfig extends WebConfig {

    /**
     * 覆盖基础配置的CORS设置，针对API层调整
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
} 