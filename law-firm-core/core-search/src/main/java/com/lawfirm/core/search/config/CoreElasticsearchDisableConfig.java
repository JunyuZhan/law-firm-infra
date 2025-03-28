package com.lawfirm.core.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Elasticsearch禁用配置类
 * 当elasticsearch.enabled=false时，提供一个空的RestClient Bean
 * 防止自动配置尝试创建ElasticSearch客户端
 */
@Configuration("coreElasticsearchDisableConfig")
@ConditionalOnProperty(prefix = "elasticsearch", name = "enabled", havingValue = "false", matchIfMissing = true)
public class CoreElasticsearchDisableConfig {

    /**
     * 提供一个空的RestClient Bean
     * 当elasticsearch.enabled=false时，阻止Spring Boot自动配置创建真实的RestClient
     */
    @Bean("coreDisabledRestClient")
    @Primary
    public RestClient disabledRestClient() {
        return null;
    }
    
    /**
     * 提供一个空的ElasticsearchTransport Bean
     */
    @Bean("coreDisabledElasticsearchTransport")
    @Primary
    public ElasticsearchTransport disabledElasticsearchTransport() {
        return null;
    }
    
    /**
     * 提供一个空的ElasticsearchClient Bean
     */
    @Bean("coreDisabledElasticsearchClient")
    @Primary
    public ElasticsearchClient disabledElasticsearchClient() {
        return null;
    }
} 