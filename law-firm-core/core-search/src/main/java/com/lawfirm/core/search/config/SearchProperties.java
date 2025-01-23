package com.lawfirm.core.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索配置属性
 */
@Data
@ConfigurationProperties(prefix = "search")
public class SearchProperties {
    
    /**
     * Elasticsearch 配置
     */
    private ElasticsearchConfig elasticsearch = new ElasticsearchConfig();
    
    /**
     * 索引配置列表
     */
    private List<IndexConfig> indices = new ArrayList<>();
    
    @Data
    public static class ElasticsearchConfig {
        /**
         * 主机地址
         */
        private List<String> hosts = new ArrayList<>();
        
        /**
         * 用户名
         */
        private String username;
        
        /**
         * 密码
         */
        private String password;
        
        /**
         * 连接超时时间（毫秒）
         */
        private int connectTimeout = 5000;
        
        /**
         * 套接字超时时间（毫秒）
         */
        private int socketTimeout = 60000;
        
        /**
         * 最大重试超时时间（毫秒）
         */
        private int maxRetryTimeout = 60000;
    }
    
    @Data
    public static class IndexConfig {
        /**
         * 索引名称
         */
        private String name;
        
        /**
         * 索引别名
         */
        private String alias;
        
        /**
         * 分片数
         */
        private int shards = 1;
        
        /**
         * 副本数
         */
        private int replicas = 1;
        
        /**
         * 刷新间隔（秒）
         */
        private int refreshInterval = 1;
        
        /**
         * 字段配置列表
         */
        private List<FieldConfig> fields = new ArrayList<>();
    }
    
    @Data
    public static class FieldConfig {
        /**
         * 字段名称
         */
        private String name;
        
        /**
         * 字段类型
         */
        private String type;
        
        /**
         * 是否分词
         */
        private boolean analyzed = true;
        
        /**
         * 是否索引
         */
        private boolean indexed = true;
        
        /**
         * 是否存储
         */
        private boolean stored = true;
        
        /**
         * 分词器
         */
        private String analyzer = "ik_max_word";
        
        /**
         * 搜索分词器
         */
        private String searchAnalyzer = "ik_smart";
        
        /**
         * 权重
         */
        private float boost = 1.0f;
    }
} 