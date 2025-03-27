package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * JPA配置
 * 
 * 用于禁用JPA相关自动配置，避免应用程序启动时自动初始化JPA
 */
@Configuration
@ConditionalOnProperty(name = "spring.jpa.enabled", havingValue = "false", matchIfMissing = true)
public class JpaConfig {
    
    /**
     * 提供一个EntityManagerFactory的Dummy实现，避免应用程序启动报错
     */
    @Bean("entityManagerFactory")
    @Primary
    public Object emptyEntityManagerFactory() {
        // 返回一个空的Map作为EntityManagerFactory的替代品
        return new HashMap<>();
    }
} 