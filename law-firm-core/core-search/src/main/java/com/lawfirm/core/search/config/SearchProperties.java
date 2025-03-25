package com.lawfirm.core.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 搜索配置属性类
 * 用于绑定application-search.yml中的配置
 */
@Data
@Validated
@ConfigurationProperties(prefix = "search")
public class SearchProperties {

    /**
     * 是否启用搜索模块
     */
    private boolean enabled = true;

    /**
     * 索引配置
     */
    private Index index = new Index();

    /**
     * 索引配置类
     */
    @Data
    public static class Index {
        /**
         * 案件索引配置
         */
        private IndexConfig caseIndex = new IndexConfig("law_firm_case", "30s");
        
        /**
         * 文档索引配置
         */
        private IndexConfig document = new IndexConfig("law_firm_document", "1m");
        
        /**
         * 客户索引配置
         */
        private IndexConfig client = new IndexConfig("law_firm_client", "1m");
        
        /**
         * 合同索引配置
         */
        private IndexConfig contract = new IndexConfig("law_firm_contract", "1m");
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