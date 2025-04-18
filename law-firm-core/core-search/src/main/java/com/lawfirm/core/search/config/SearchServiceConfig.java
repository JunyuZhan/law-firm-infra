package com.lawfirm.core.search.config;

import com.lawfirm.core.search.service.impl.SearchServiceImpl;
import com.lawfirm.model.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务配置
 */
@Slf4j
@Configuration
public class SearchServiceConfig {

    /**
     * 搜索服务实现
     */
    @Bean(name = "searchServiceImpl")
    @Primary
    public SearchService searchServiceImpl() {
        log.info("注册搜索服务实现: searchServiceImpl");
        return new SearchServiceImpl();
    }

    /**
     * 索引服务实现
     */
    @Bean(name = "searchIndexServiceImpl")
    public Object searchIndexServiceImpl() {
        log.info("注册索引服务实现: searchIndexServiceImpl");
        return new Object() {
            public void indexDocument(String indexName, String id, Map<String, Object> document) {
                log.info("索引文档: index={}, id={}, document={}", indexName, id, document);
            }
            
            public void deleteDocument(String indexName, String id) {
                log.info("删除文档索引: index={}, id={}", indexName, id);
            }
            
            public List<Map<String, Object>> searchDocuments(String indexName, Map<String, Object> searchParams) {
                log.info("搜索文档: index={}, params={}", indexName, searchParams);
                return Collections.emptyList();
            }
        };
    }
} 