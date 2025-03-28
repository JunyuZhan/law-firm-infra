package com.lawfirm.core.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Elasticsearch配置类
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ElasticsearchProperties.class)
@ConditionalOnProperty(prefix = "elasticsearch", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ElasticsearchConfig {

    private final ElasticsearchProperties properties;

    @Bean
    public RestClient restClient() {
        List<String> nodes = properties.getCluster().getNodes();
        HttpHost[] hosts = nodes.stream()
                .map(node -> {
                    String[] parts = node.split(":");
                    return new HttpHost(parts[0], Integer.parseInt(parts[1]), "http");
                })
                .toArray(HttpHost[]::new);

        return RestClient.builder(hosts)
                .setRequestConfigCallback(builder -> builder
                        .setConnectTimeout(properties.getClient().getConnectTimeout())
                        .setSocketTimeout(properties.getClient().getSocketTimeout()))
                .build();
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        return new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        return new ElasticsearchClient(transport);
    }
} 