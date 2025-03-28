package com.lawfirm.document.config;

import com.lawfirm.document.config.properties.SearchProperties;
import com.lawfirm.document.service.strategy.search.DatabaseSearchStrategy;
import com.lawfirm.document.service.strategy.search.SearchStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文档模块搜索配置类
 * 由于ElasticSearch是付费服务，这里默认使用数据库搜索策略
 */
@Slf4j
@Configuration("documentSearchConfig")
@RequiredArgsConstructor
public class DocumentSearchConfig {

    private final SearchProperties searchProperties;
    private final DatabaseSearchStrategy databaseSearchStrategy;
    
    // 使用@Slf4j注解自动生成log变量，无需手动定义

    /**
     * 配置默认搜索策略
     */
    @Bean("documentDefaultSearchStrategy")
    public SearchStrategy defaultSearchStrategy() {
        // 总是使用数据库搜索策略，避免依赖ElasticSearch
        log.info("配置默认搜索策略: database");
        return databaseSearchStrategy;
    }
} 