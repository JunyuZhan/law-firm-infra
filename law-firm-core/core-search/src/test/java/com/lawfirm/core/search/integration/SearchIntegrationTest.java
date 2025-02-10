package com.lawfirm.core.search.integration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import com.lawfirm.core.search.model.SearchRequest;
import com.lawfirm.core.search.service.impl.ElasticsearchServiceImpl;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class SearchIntegrationTest {

    @Container
    static ElasticsearchContainer elasticsearch = new ElasticsearchContainer(
        DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.12.0")
    );

    @Autowired
    private ElasticsearchServiceImpl searchService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private static final String TEST_INDEX = "test_index";

    @DynamicPropertySource
    static void registerElasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", elasticsearch::getHttpHostAddress);
    }

    @BeforeEach
    void setUp() throws IOException {
        // 1. 创建索引
        CreateIndexResponse createResponse = elasticsearchClient.indices().create(c -> c
            .index(TEST_INDEX)
            .mappings(m -> m
                .properties("id", p -> p.long_(l -> l))
                .properties("title", p -> p.text(t -> t
                    .analyzer("ik_max_word")
                    .searchAnalyzer("ik_smart")))
                .properties("content", p -> p.text(t -> t
                    .analyzer("ik_max_word")
                    .searchAnalyzer("ik_smart")))
            ));
        assertTrue(createResponse.acknowledged());

        // 2. 添加测试数据
        TestDocument doc1 = new TestDocument(1L, "测试文档1", "这是第一个测试文档的内容");
        TestDocument doc2 = new TestDocument(2L, "示例文档2", "这是第二个示例文档的内容");
        
        elasticsearchClient.index(i -> i
            .index(TEST_INDEX)
            .id("1")
            .document(doc1));
        
        elasticsearchClient.index(i -> i
            .index(TEST_INDEX)
            .id("2")
            .document(doc2));

        // 3. 刷新索引
        elasticsearchClient.indices().refresh(r -> r.index(TEST_INDEX));
    }

    @AfterEach
    void tearDown() throws IOException {
        DeleteIndexResponse deleteResponse = elasticsearchClient.indices().delete(d -> d
            .index(TEST_INDEX));
        assertTrue(deleteResponse.acknowledged());
    }

    @Test
    void search_ByKeyword() {
        // Given
        SearchRequest request = new SearchRequest()
            .setIndex(TEST_INDEX)
            .setKeyword("测试")
            .setPage(1)
            .setSize(10);

        Map<String, Float> fields = new HashMap<>();
        fields.put("title", 2.0f);
        fields.put("content", 1.0f);
        request.setFields(fields);

        // When
        com.lawfirm.core.search.model.SearchResponse<TestDocument> response = 
            searchService.search(request, TestDocument.class);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getTotal());
        List<TestDocument> hits = response.getHits();
        assertEquals(1, hits.size());
        TestDocument doc = hits.get(0);
        assertEquals("测试文档1", doc.getTitle());
    }

    @Test
    void search_WithHighlight() {
        // Given
        SearchRequest request = new SearchRequest()
            .setIndex(TEST_INDEX)
            .setKeyword("测试")
            .setPage(1)
            .setSize(10)
            .setHighlightFields(List.of("title", "content"));

        Map<String, Float> fields = new HashMap<>();
        fields.put("title", 2.0f);
        fields.put("content", 1.0f);
        request.setFields(fields);

        // When
        com.lawfirm.core.search.model.SearchResponse<TestDocument> response = 
            searchService.search(request, TestDocument.class);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getTotal());
        List<TestDocument> hits = response.getHits();
        assertEquals(1, hits.size());
        TestDocument doc = hits.get(0);
        assertTrue(doc.getTitle().contains("<em>测试</em>"));
    }

    @Data
    static class TestDocument {
        private Long id;
        private String title;
        private String content;

        public TestDocument() {}

        public TestDocument(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
} 