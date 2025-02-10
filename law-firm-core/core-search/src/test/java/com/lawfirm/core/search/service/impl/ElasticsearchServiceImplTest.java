package com.lawfirm.core.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.lawfirm.core.search.model.SearchRequest;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElasticsearchServiceImplTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private ElasticsearchServiceImpl searchService;

    private SearchRequest searchRequest;
    private TestDocument testDocument;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        searchRequest = new SearchRequest()
            .setIndex("test_index")
            .setKeyword("test")
            .setPage(1)
            .setSize(10);

        Map<String, Float> fields = new HashMap<>();
        fields.put("title", 2.0f);
        fields.put("content", 1.0f);
        searchRequest.setFields(fields);

        testDocument = new TestDocument();
        testDocument.setId(1L);
        testDocument.setTitle("Test Title");
        testDocument.setContent("Test Content");
    }

    @Test
    void search_Success() throws IOException {
        // Given
        SearchResponse<TestDocument> mockResponse = mock(SearchResponse.class);
        Hit<TestDocument> mockHit = mock(Hit.class);
        when(mockHit.source()).thenReturn(testDocument);
        when(mockResponse.hits()).thenReturn(mock(SearchResponse.Hits.class));
        when(mockResponse.hits().hits()).thenReturn(List.of(mockHit));
        when(mockResponse.hits().total()).thenReturn(mock(SearchResponse.Total.class));
        when(mockResponse.hits().total().value()).thenReturn(1L);

        when(elasticsearchClient.search(any(), eq(TestDocument.class))).thenReturn(mockResponse);

        // When
        com.lawfirm.core.search.model.SearchResponse<TestDocument> response = 
            searchService.search(searchRequest, TestDocument.class);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getTotal());
        assertEquals(1, response.getHits().size());
        assertEquals(testDocument, response.getHits().get(0));
        verify(elasticsearchClient).search(any(), eq(TestDocument.class));
    }

    @Test
    void search_WithHighlight() throws IOException {
        // Given
        searchRequest.setHighlightFields(Arrays.asList("title", "content"));
        
        SearchResponse<TestDocument> mockResponse = mock(SearchResponse.class);
        Hit<TestDocument> mockHit = mock(Hit.class);
        when(mockHit.source()).thenReturn(testDocument);
        
        Map<String, List<String>> highlights = new HashMap<>();
        highlights.put("title", List.of("<em>Test</em> Title"));
        when(mockHit.highlight()).thenReturn(highlights);
        
        when(mockResponse.hits()).thenReturn(mock(SearchResponse.Hits.class));
        when(mockResponse.hits().hits()).thenReturn(List.of(mockHit));
        when(mockResponse.hits().total()).thenReturn(mock(SearchResponse.Total.class));
        when(mockResponse.hits().total().value()).thenReturn(1L);

        when(elasticsearchClient.search(any(), eq(TestDocument.class))).thenReturn(mockResponse);

        // When
        com.lawfirm.core.search.model.SearchResponse<TestDocument> response = 
            searchService.search(searchRequest, TestDocument.class);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getTotal());
        assertEquals(1, response.getHits().size());
        TestDocument result = response.getHits().get(0);
        assertEquals("<em>Test</em> Title", result.getTitle());
        verify(elasticsearchClient).search(any(), eq(TestDocument.class));
    }

    @Test
    void search_WithError() throws IOException {
        // Given
        when(elasticsearchClient.search(any(), eq(TestDocument.class)))
            .thenThrow(new IOException("Search failed"));

        // When & Then
        assertThrows(RuntimeException.class, () -> 
            searchService.search(searchRequest, TestDocument.class));
        verify(elasticsearchClient).search(any(), eq(TestDocument.class));
    }

    @Data
    static class TestDocument {
        private Long id;
        private String title;
        private String content;
    }
} 