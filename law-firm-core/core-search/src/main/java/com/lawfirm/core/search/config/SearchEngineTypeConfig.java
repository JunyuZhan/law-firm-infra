package com.lawfirm.core.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索引擎类型配置
 * <p>
 * 用于配置并初始化搜索引擎类型
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "lawfirm.search.enabled", havingValue = "true", matchIfMissing = true)
public class SearchEngineTypeConfig {

    @Value("${lawfirm.search.type:database}")
    private String searchType;
    
    /**
     * 初始化搜索引擎类型
     * 将类型存储在系统属性中，以便其他组件使用
     */
    @Bean(name = "searchEngineType")
    public Object searchEngineTypeBean() {
        log.info("核心搜索模块初始化, 使用搜索类型: {}", searchType);
        
        // 设置系统属性，供其他组件参考
        System.setProperty("lawfirm.search.current.type", searchType);
        
        return searchType;
    }
} 