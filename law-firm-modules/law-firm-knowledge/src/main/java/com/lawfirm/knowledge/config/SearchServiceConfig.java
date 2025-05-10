package com.lawfirm.knowledge.config;

import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.knowledge.service.SearchIntegrationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 知识库搜索服务配置
 */
@Slf4j
@Configuration("knowledgeSearchServiceConfig")
public class SearchServiceConfig {

    @Autowired
    @Qualifier("coreSearchServiceImpl")
    private SearchService coreSearchService;
    
    @Autowired
    @Qualifier("luceneIndexHandler")
    private IndexHandler indexHandler;

    /**
     * 知识库搜索服务实现
     * 注意：复用已有的SearchIntegrationService
     */
    @Bean(name = "knowledgeSearchIntegrationService")
    public SearchIntegrationService knowledgeSearchIntegrationService() {
        log.info("注册知识库搜索服务实现: knowledgeSearchIntegrationService");
        return new SearchIntegrationService();
    }
} 