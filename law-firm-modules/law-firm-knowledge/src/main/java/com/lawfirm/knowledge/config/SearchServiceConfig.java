package com.lawfirm.knowledge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索服务配置
 */
@Slf4j
@Configuration
public class SearchServiceConfig {

    /**
     * 搜索服务空实现
     */
    @Bean(name = "knowledgeSearchServiceImpl")
    @ConditionalOnMissingBean(name = "searchServiceImpl")
    public Object searchServiceImpl() {
        log.warn("未检测到搜索服务实现，使用默认空实现");
        // 由于SearchService接口可能有多个方法，这里返回一个基本实现
        return new Object() {
            public Page<Map<String, Object>> search(String keyword, String indexName, Pageable pageable) {
                log.info("搜索请求: keyword={}, index={}, page={}", keyword, indexName, pageable);
                return Page.empty(pageable);
            }
        };
    }

    /**
     * 索引服务空实现
     */
    @Bean(name = "knowledgeIndexServiceImpl")
    @ConditionalOnMissingBean(name = "indexServiceImpl")
    public Object indexServiceImpl() {
        log.warn("未检测到索引服务实现，使用默认空实现");
        // 由于IndexService接口可能有多个方法，这里返回一个基本实现
        return new Object() {
            public boolean indexDocument(String indexName, String id, Map<String, Object> document) {
                log.info("索引文档: index={}, id={}, document={}", indexName, id, document);
                return true;
            }
            
            public boolean deleteDocument(String indexName, String id) {
                log.info("删除索引文档: index={}, id={}", indexName, id);
                return true;
            }
        };
    }
} 