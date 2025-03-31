package com.lawfirm.core.search.config;

import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索配置提供者实现
 */
@Slf4j
@Configuration
public class SearchPropertiesProviderImpl {
    
    /**
     * 搜索配置提供者
     */
    @Bean(name = "searchPropertiesProvider")
    @Primary
    @ConditionalOnProperty(name = "lawfirm.search.enabled", havingValue = "true", matchIfMissing = true)
    public SearchPropertiesProvider searchPropertiesProvider(SearchProperties properties) {
        log.info("创建搜索配置提供者, 引擎类型: {}", properties.getEngine());
        return () -> {
            // 确保配置被正确加载
            if (properties.getEngine() == null || properties.getEngine().isEmpty()) {
                properties.setEngine("lucene");
            }
            
            // 确保Lucene配置存在
            if (properties.getLucene().getIndexDir() == null || properties.getLucene().getIndexDir().isEmpty()) {
                properties.getLucene().setIndexDir("./lucene/index");
            }
            
            return properties;
        };
    }
} 