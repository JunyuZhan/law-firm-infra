package com.lawfirm.common.data.search.impl;

import com.lawfirm.common.data.search.SearchService;
import com.lawfirm.common.data.search.SearchTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Elasticsearch搜索服务实现
 */
@Slf4j
@Service
public class ElasticsearchService<T> implements SearchService<T> {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private final Class<T> documentClass;
    private final String indexName;

    public ElasticsearchService(Class<T> documentClass, String indexName) {
        this.documentClass = documentClass;
        this.indexName = indexName;
    }

    @Override
    public T save(T document) {
        return elasticsearchOperations.save(document, IndexCoordinates.of(indexName));
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> documents) {
        return elasticsearchOperations.save(documents, IndexCoordinates.of(indexName));
    }

    @Override
    public void delete(String id) {
        elasticsearchOperations.delete(id, documentClass);
    }

    @Override
    public SearchHits<T> search(SearchTemplate searchTemplate) {
        NativeSearchQuery searchQuery = searchTemplate.buildQuery();
        return elasticsearchOperations.search(searchQuery, documentClass);
    }

    @Override
    public Page<T> searchPage(SearchTemplate searchTemplate) {
        NativeSearchQuery searchQuery = searchTemplate.buildQuery();
        SearchHits<T> searchHits = elasticsearchOperations.search(searchQuery, documentClass);
        
        List<T> content = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
                
        return new PageImpl<>(content, 
                searchTemplate.getPageable(), 
                searchHits.getTotalHits());
    }
} 