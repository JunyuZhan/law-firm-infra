package com.lawfirm.document.config;

import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.core.search.service.impl.SearchServiceImpl;
import com.lawfirm.core.search.handler.DocumentHandler;
import com.lawfirm.core.search.handler.SearchHandler;
import com.lawfirm.model.search.mapper.SearchDocMapper;
import com.lawfirm.document.config.properties.DocumentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档搜索配置
 */
@Configuration
@EnableConfigurationProperties(DocumentProperties.class)
public class DocumentSearchConfig {

    /**
     * 配置搜索服务
     */
    @Bean
    public SearchService searchService(SearchDocMapper searchDocMapper,
                                     DocumentHandler documentHandler,
                                     SearchHandler searchHandler) {
        return new SearchServiceImpl(searchDocMapper, documentHandler, searchHandler);
    }
} 