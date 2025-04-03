package com.lawfirm.core.search.factory;

import com.lawfirm.core.search.config.SearchProperties;
import com.lawfirm.model.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 搜索引擎工厂
 * 根据配置选择适当的搜索策略
 */
@Slf4j
@Component
public class SearchEngineFactory {

    @Autowired
    private SearchProperties searchProperties;

    /**
     * Lucene搜索服务（当配置为lucene时注入）
     */
    @Autowired(required = false)
    @Qualifier("luceneSearchService")
    private SearchService luceneSearchService;

    /**
     * 数据库搜索服务（当配置为database时注入）
     */
    @Autowired(required = false)
    @Qualifier("databaseSearchService")
    private SearchService databaseSearchService;

    @PostConstruct
    public void init() {
        log.info("初始化搜索引擎工厂，当前搜索引擎类型: {}", searchProperties.getType());
    }

    /**
     * 获取当前配置的搜索服务
     */
    public SearchService getSearchService() {
        String type = searchProperties.getType();
        
        if ("lucene".equals(type)) {
            if (luceneSearchService != null) {
                return luceneSearchService;
            } else {
                log.warn("未找到Lucene搜索服务实现，将使用默认的数据库搜索");
            }
        }
        
        if ("database".equals(type) || databaseSearchService != null) {
            return databaseSearchService;
        }
        
        log.error("未找到任何可用的搜索服务实现");
        throw new IllegalStateException("未配置任何搜索服务实现");
    }
} 