package com.lawfirm.core.search.config;

import com.lawfirm.core.search.handler.DocumentHandler;
import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.core.search.handler.LuceneManager;
import com.lawfirm.core.search.handler.impl.LuceneDocumentHandler;
import com.lawfirm.core.search.handler.LuceneIndexHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Lucene搜索配置
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "lucene")
public class LuceneSearchConfig {

    /**
     * Lucene配置初始化
     */
    public LuceneSearchConfig() {
        log.info("初始化Lucene搜索配置");
    }

    /**
     * 配置标准分析器
     */
    @Bean(name = "standardAnalyzer")
    public Analyzer standardAnalyzer() {
        return new StandardAnalyzer();
    }

    /**
     * 配置IK分析器
     * IK分析器是一个开源的，基于java语言开发的轻量级的中文分词工具包
     */
    @Bean(name = "ikAnalyzer")
    public Analyzer ikAnalyzer() {
        return new IKAnalyzer(true);
    }

    /**
     * 创建 LuceneManager Bean
     * 需要注入 SearchProperties
     */
    @Bean
    public LuceneManager luceneManager(SearchProperties searchProperties) {
        return new LuceneManager(searchProperties);
    }

    /**
     * 创建 LuceneDocumentHandler Bean
     * 需要注入 LuceneManager
     */
    @Bean
    public DocumentHandler luceneDocumentHandler(LuceneManager luceneManager) {
        return new LuceneDocumentHandler(luceneManager);
    }
    
    /**
     * 创建 LuceneIndexHandler Bean
     * 需要注入 SearchProperties 和 Analyzer (使用 @Qualifier 指定 ikAnalyzer)
     * 注意 Bean 的名称与之前 @Component 中定义的一致 ("luceneIndexHandler")
     */
    @Bean(name = "luceneIndexHandler")
    public IndexHandler luceneIndexHandler(SearchProperties searchProperties, @Qualifier("ikAnalyzer") Analyzer analyzer) {
        return new LuceneIndexHandler(searchProperties, analyzer);
    }
}