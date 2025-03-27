package com.lawfirm.document.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文档搜索配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "law.firm.document.search")
public class SearchProperties {
    
    /**
     * 搜索引擎类型：elasticsearch/solr
     */
    private String type = "elasticsearch";
    
    /**
     * 索引名称
     */
    private String indexName = "documents";
    
    /**
     * 分片数
     */
    private int numberOfShards = 5;
    
    /**
     * 副本数
     */
    private int numberOfReplicas = 1;
    
    /**
     * 刷新间隔（秒）
     */
    private int refreshInterval = 1;
    
    /**
     * 是否启用中文分词
     */
    private boolean enableChineseAnalyzer = true;
    
    /**
     * 分词器类型：ik/pinyin
     */
    private String analyzerType = "ik";
    
    /**
     * 是否启用拼音搜索
     */
    private boolean enablePinyinSearch = true;
    
    /**
     * 是否启用同义词
     */
    private boolean enableSynonyms = true;
    
    /**
     * 同义词词典路径
     */
    private String synonymsPath = "data/synonyms.txt";
    
    /**
     * 是否启用高亮
     */
    private boolean enableHighlight = true;
    
    /**
     * 高亮字段
     */
    private String[] highlightFields = {"title", "content"};
    
    /**
     * 高亮前缀
     */
    private String highlightPreTag = "<em>";
    
    /**
     * 高亮后缀
     */
    private String highlightPostTag = "</em>";
    
    /**
     * 是否启用建议器
     */
    private boolean enableSuggest = true;
    
    /**
     * 建议器字段
     */
    private String[] suggestFields = {"title", "content"};
    
    /**
     * 建议器大小
     */
    private int suggestSize = 5;
    
    /**
     * 是否启用缓存
     */
    private boolean enableCache = true;
    
    /**
     * 缓存过期时间（分钟）
     */
    private int cacheExpireMinutes = 30;
    
    /**
     * 缓存最大条数
     */
    private int cacheMaxSize = 1000;
}
