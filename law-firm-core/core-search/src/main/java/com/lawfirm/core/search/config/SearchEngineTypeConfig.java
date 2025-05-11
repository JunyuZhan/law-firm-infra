package com.lawfirm.core.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索引擎类型配置
 * 用于设置搜索引擎类型，确保Lucene搜索引擎启用
 */
@Slf4j
@Configuration
public class SearchEngineTypeConfig implements CommandLineRunner {

    @Autowired
    private SearchProperties searchProperties;

    @Override
    public void run(String... args) {
        // 强制设置搜索引擎类型为Lucene
        searchProperties.setSearchEngineType("lucene");
        log.info("设置搜索引擎类型系统属性: {}", searchProperties.getSearchEngineType());
    }
} 