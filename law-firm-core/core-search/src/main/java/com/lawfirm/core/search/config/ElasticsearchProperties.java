package com.lawfirm.core.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * Elasticsearch配置属性
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    /**
     * 是否启用ElasticSearch
     */
    private boolean enabled = false;

    @NestedConfigurationProperty
    private Cluster cluster = new Cluster();

    @NestedConfigurationProperty
    private Client client = new Client();

    @NestedConfigurationProperty
    private Index index = new Index();

    @Data
    public static class Cluster {
        /**
         * 集群名称
         */
        private String name = "law-firm-cluster";

        /**
         * 集群节点
         */
        private List<String> nodes;
    }

    @Data
    public static class Client {
        /**
         * 连接超时时间(ms)
         */
        private int connectTimeout = 5000;

        /**
         * Socket超时时间(ms)
         */
        private int socketTimeout = 60000;

        /**
         * 最大重试次数
         */
        private int maxRetries = 3;
    }

    @Data
    public static class Index {
        /**
         * 默认分片数
         */
        private int numberOfShards = 3;

        /**
         * 默认副本数
         */
        private int numberOfReplicas = 1;
    }
} 