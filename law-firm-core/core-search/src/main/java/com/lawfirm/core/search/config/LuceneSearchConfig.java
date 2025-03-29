package com.lawfirm.core.search.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Lucene搜索配置
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "search", name = "engine", havingValue = "lucene")
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
} 