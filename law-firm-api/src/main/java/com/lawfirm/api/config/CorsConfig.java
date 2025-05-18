package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * API模块跨域配置
 * 提供更安全的CORS设置，限制允许的来源域名
 */
@Configuration("apiCorsConfig")
@Order(1) // 确保CORS过滤器优先级高
@Slf4j
public class CorsConfig {
    
    @Value("${cors.allowed-origins:https://admin.lawfirm.com,https://app.lawfirm.com}")
    private String[] allowedOrigins;
    
    /**
     * 配置CORS过滤器
     * 默认只允许特定域名访问，生产环境通过环境变量配置
     */
    @Bean("apiCorsFilter")
    public CorsFilter corsFilter() {
        log.info("初始化API模块CORS配置，允许的域名: {}", String.join(", ", allowedOrigins));
        
        CorsConfiguration config = new CorsConfiguration();
        // 仅允许特定的域名
        for (String origin : allowedOrigins) {
            if (!"*".equals(origin)) {
                config.addAllowedOrigin(origin);
            }
        }
        
        // 如果配置了通配符，则使用allowedOriginPatterns
        if (containsWildcard(allowedOrigins)) {
            log.warn("检测到通配符域名配置，这在生产环境中存在安全风险");
            config.addAllowedOriginPattern("*");
        }
        
        // 允许凭证
        config.setAllowCredentials(true);
        
        // 限制允许的HTTP方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        
        // 限制允许的请求头
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("X-API-Version");
        config.addAllowedHeader("X-Idempotent-Token");
        
        // 设置预检请求的有效期（秒）
        config.setMaxAge(3600L);
        
        // 应用CORS配置到所有API路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
    
    /**
     * 检查是否包含通配符域名
     */
    private boolean containsWildcard(String[] origins) {
        if (origins == null || origins.length == 0) {
            return false;
        }
        
        for (String origin : origins) {
            if ("*".equals(origin)) {
                return true;
            }
        }
        
        return false;
    }
} 