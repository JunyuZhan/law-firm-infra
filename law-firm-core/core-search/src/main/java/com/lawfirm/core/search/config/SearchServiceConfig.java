package com.lawfirm.core.search.config;

import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.core.search.service.impl.IndexServiceImpl;
import com.lawfirm.core.search.service.impl.SearchServiceImpl;
import com.lawfirm.model.search.mapper.SearchIndexMapper;
import com.lawfirm.model.search.service.IndexService;
import com.lawfirm.model.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 搜索服务配置
 */
@Slf4j
@Configuration("coreSearchServiceConfig")
public class SearchServiceConfig {

    @Autowired
    private SearchIndexMapper searchIndexMapper;
    
    @Autowired
    @Qualifier("luceneIndexHandler")
    private IndexHandler indexHandler;

    /**
     * 搜索服务实现
     */
    @Bean(name = "coreSearchServiceImpl")
    public SearchService searchServiceImpl() {
        log.info("注册搜索服务实现: coreSearchServiceImpl");
        return new SearchServiceImpl();
    }

    /**
     * 索引服务实现
     */
    @Bean(name = "coreIndexServiceImpl")
    public IndexService indexServiceImpl() {
        log.info("注册索引服务实现: coreIndexServiceImpl (使用 SearchIndexMapper 和 luceneIndexHandler)");
        return new IndexServiceImpl(searchIndexMapper, indexHandler);
    }
} 