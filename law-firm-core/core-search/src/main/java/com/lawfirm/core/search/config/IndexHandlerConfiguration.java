package com.lawfirm.core.search.config;

import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.core.search.handler.LuceneIndexHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 索引处理器配置类
 * 确保始终提供luceneIndexHandler Bean
 */
@Slf4j
@Configuration
public class IndexHandlerConfiguration {
    
    /**
     * 当没有IK分析器时提供一个默认的
     */
    @Bean(name = "backupIkAnalyzer")
    @ConditionalOnMissingBean(name = "ikAnalyzer")
    public Analyzer backupIkAnalyzer() {
        log.info("创建备用IK分析器");
        return new IKAnalyzer(true);
    }
    
    /**
     * 当没有luceneIndexHandler时提供一个默认的
     */
    @Bean(name = "luceneIndexHandler")
    @Primary
    @ConditionalOnMissingBean(name = "luceneIndexHandler")
    public IndexHandler luceneIndexHandler(SearchProperties searchProperties, 
            @Qualifier("backupIkAnalyzer") Analyzer analyzer) {
        log.info("创建强制启用的Lucene索引处理器");
        // 确保searchProperties中的searchEngineType设置为lucene
        searchProperties.setSearchEngineType("lucene");
        return new LuceneIndexHandler(searchProperties, analyzer);
    }
} 