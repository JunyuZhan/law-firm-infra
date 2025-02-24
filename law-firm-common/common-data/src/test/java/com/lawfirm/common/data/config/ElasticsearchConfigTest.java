package com.lawfirm.common.data.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.elasticsearch.enabled=true",
    "spring.elasticsearch.uris=http://localhost:9200"
})
class ElasticsearchConfigTest {

    @Autowired(required = false)
    private ElasticsearchClient elasticsearchClient;

    @Test
    void elasticsearchClient_ShouldBeConfigured() {
        assertNotNull(elasticsearchClient, "Elasticsearch client should be configured when enabled");
    }

    @Test
    void elasticsearchClient_ShouldSupportBasicOperations() {
        assertDoesNotThrow(() -> {
            assertNotNull(elasticsearchClient.info(), "Elasticsearch client should support basic operations");
        }, "Elasticsearch client operations should not throw exceptions");
    }
} 