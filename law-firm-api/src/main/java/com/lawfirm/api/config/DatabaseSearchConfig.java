package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.core.search.service.DatabaseSearchService;

/**
 * 数据库搜索配置
 * <p>
 * 当search.engine.type=database时生效，用于提供数据库查询的实现。
 */
@Slf4j
@Configuration("databaseSearchConfig")
@ConditionalOnProperty(name = "search.engine.type", havingValue = "database", matchIfMissing = true)
public class DatabaseSearchConfig {

    /**
     * 初始化配置
     */
    @Bean("databaseSearchInitialized")
    @ConditionalOnMissingBean(name = "searchEngineInitialized")
    public String initDatabaseSearch() {
        log.info("使用数据库作为搜索引擎");
        return "databaseSearchInitialized";
    }

    /**
     * 数据库搜索服务
     * 如果容器中没有，则创建一个
     */
    @Bean
    @ConditionalOnMissingBean(DatabaseSearchService.class)
    public DatabaseSearchService databaseSearchService() {
        log.info("创建数据库搜索服务");
        return new DatabaseSearchService();
    }
} 