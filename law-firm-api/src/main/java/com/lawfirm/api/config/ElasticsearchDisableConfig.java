package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Elasticsearch禁用配置类
 * 当elasticsearch.enabled=false时生效
 */
@Configuration
@ConditionalOnProperty(name = "elasticsearch.enabled", havingValue = "false", matchIfMissing = false)
public class ElasticsearchDisableConfig {

    /**
     * 提供一个空的RestClient Bean替代
     * 避免ElasticsearchHealthContributor初始化失败
     */
    @Bean
    @Primary
    public org.elasticsearch.client.RestClient disabledRestClient() {
        return null;
    }
} 