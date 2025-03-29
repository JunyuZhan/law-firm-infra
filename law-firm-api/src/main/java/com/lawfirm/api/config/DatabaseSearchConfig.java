package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.core.search.service.DatabaseSearchService;

/**
 * 搜索服务配置
 * 提供搜索功能，使用core-search模块
 */
@Slf4j
@Configuration
public class DatabaseSearchConfig {

    /**
     * 数据库搜索服务
     * 如果容器中没有，则创建一个
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(DatabaseSearchService.class)
    public DatabaseSearchService databaseSearchService() {
        log.info("创建数据库搜索服务");
        return new DatabaseSearchService();
    }
} 