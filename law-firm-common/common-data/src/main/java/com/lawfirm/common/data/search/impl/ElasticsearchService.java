package com.lawfirm.common.data.search.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.lawfirm.common.data.search.SearchService;
import com.lawfirm.common.data.search.SearchTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Elasticsearch搜索服务实现
 */
@Service
@RequiredArgsConstructor
public class ElasticsearchService implements SearchService {

    private final SearchTemplate searchTemplate;

    @Override
    public <T> List<T> search(String index, Query query, Class<T> clazz) throws IOException {
        return searchTemplate.search(index, query, clazz);
    }
} 