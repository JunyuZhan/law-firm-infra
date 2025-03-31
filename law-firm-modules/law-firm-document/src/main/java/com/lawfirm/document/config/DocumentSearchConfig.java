package com.lawfirm.document.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import com.lawfirm.document.config.properties.SearchProperties;
import com.lawfirm.document.service.strategy.search.DatabaseSearchStrategy;
import com.lawfirm.document.service.strategy.search.SearchStrategy;
import com.lawfirm.model.document.mapper.DocumentMapper;

/**
 * 文档搜索配置
 * 简化配置，始终使用数据库搜索
 */
@Slf4j
@Configuration
public class DocumentSearchConfig {

    private final DocumentMapper documentMapper;

    public DocumentSearchConfig(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
        log.info("初始化文档搜索配置，使用数据库搜索策略");
    }

    /**
     * 创建数据库搜索策略
     */
    @Bean(name = "configDatabaseSearchStrategy")
    @ConditionalOnMissingBean(name = "databaseSearchStrategy")
    public SearchStrategy databaseSearchStrategy() {
        log.info("注册数据库搜索策略");
        return new DatabaseSearchStrategy(documentMapper);
    }
} 