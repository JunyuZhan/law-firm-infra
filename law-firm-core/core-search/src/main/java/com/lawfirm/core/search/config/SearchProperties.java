package com.lawfirm.core.search.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 搜索模块配置属性
 */
@Data
@Validated
@Component("coreSearchProperties")
@ConfigurationProperties(prefix = "law-firm.search")
public class SearchProperties {
    /**
     * 是否启用搜索功能
     */
    private boolean enabled = true;

    /**
     * 搜索引擎类型: lucene, database
     * 改名为searchEngineType避免与Spring框架保留名称"type"冲突
     */
    private String searchEngineType = "database";

    /**
     * Lucene配置
     */
    private Lucene lucene = new Lucene();

    /**
     * 索引配置
     */
    private Index index = new Index();

    /**
     * Lucene配置
     */
    @Data
    public static class Lucene {
        /**
         * 索引存储目录
         */
        private String indexDir = "${user.home}/.law-firm/lucene-index";
        
        /**
         * 内存缓冲区大小(MB)
         */
        private int ramBufferSizeMb = 32;
        
        /**
         * 最大搜索结果数
         */
        private int maxResults = 1000;
        
        /**
         * 是否启用高亮
         */
        private boolean highlightEnabled = true;
        
        /**
         * 高亮前置标签
         */
        private String highlightPreTag = "<em>";
        
        /**
         * 高亮后置标签
         */
        private String highlightPostTag = "</em>";
        
        /**
         * 分析器类型: standard, chinese, ik
         */
        private String analyzer = "ik";
    }

    /**
     * 索引配置
     */
    @Data
    public static class Index {
        /**
         * 案件索引配置
         */
        private IndexConfig caseIndex = new IndexConfig("law_firm_case", "1s");
        
        /**
         * 文档索引配置
         */
        private IndexConfig document = new IndexConfig("law_firm_document", "1s");
        
        /**
         * 客户索引配置
         */
        private IndexConfig client = new IndexConfig("law_firm_client", "1s");
        
        /**
         * 合同索引配置
         */
        private IndexConfig contract = new IndexConfig("law_firm_contract", "1s");
    }
    
    /**
     * 具体索引配置
     */
    @Data
    public static class IndexConfig {
        /**
         * 索引名称
         */
        private String name;
        
        /**
         * 刷新间隔
         */
        private String refreshInterval;
        
        public IndexConfig(String name, String refreshInterval) {
            this.name = name;
            this.refreshInterval = refreshInterval;
        }
    }
} 