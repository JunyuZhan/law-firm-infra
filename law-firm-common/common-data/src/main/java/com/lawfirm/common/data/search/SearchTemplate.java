package com.lawfirm.common.data.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 搜索模板
 */
@Component
@RequiredArgsConstructor
public class SearchTemplate {

    private final ElasticsearchClient client;

    /**
     * 执行搜索
     *
     * @param index 索引名称
     * @param query 查询条件
     * @param clazz 返回类型
     * @return 搜索结果
     */
    public <T> List<T> search(String index, Query query, Class<T> clazz) throws IOException {
        SearchRequest request = SearchRequest.of(r -> r
                .index(index)
                .query(query));

        SearchResponse<T> response = client.search(request, clazz);
        return response.hits().hits().stream()
                .map(hit -> hit.source())
                .toList();
    }
} 