package com.lawfirm.core.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 搜索自动配置
 */
@Configuration
@ComponentScan("com.lawfirm.core.search")
@EnableConfigurationProperties(SearchProperties.class)
public class SearchAutoConfiguration {
    
    @Bean
    public ElasticsearchClient elasticsearchClient(SearchProperties searchProperties) {
        // 1. 创建 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // 2. 创建 HttpHost 列表
        HttpHost[] httpHosts = searchProperties.getElasticsearch().getHosts().stream()
                .map(host -> {
                    String[] parts = host.split(":");
                    return new HttpHost(parts[0], Integer.parseInt(parts[1]), "http");
                })
                .toArray(HttpHost[]::new);
        
        // 3. 创建 RestClientBuilder
        RestClientBuilder builder = RestClient.builder(httpHosts)
                .setRequestConfigCallback(requestConfig -> requestConfig
                        .setConnectTimeout(searchProperties.getElasticsearch().getConnectTimeout())
                        .setSocketTimeout(searchProperties.getElasticsearch().getSocketTimeout()));
        
        // 4. 配置认证（如果需要）
        String username = searchProperties.getElasticsearch().getUsername();
        String password = searchProperties.getElasticsearch().getPassword();
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
            builder.setHttpClientConfigCallback(httpClient -> 
                    httpClient.setDefaultCredentialsProvider(credentialsProvider));
        }
        
        // 5. 创建 RestClient
        RestClient restClient = builder.build();
        
        // 6. 创建 Transport
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper(objectMapper));
        
        // 7. 创建 ElasticsearchClient
        return new ElasticsearchClient(transport);
    }
} 