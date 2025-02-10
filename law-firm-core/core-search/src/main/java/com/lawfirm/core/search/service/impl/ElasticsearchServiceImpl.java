package com.lawfirm.core.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.lawfirm.common.data.search.impl.ElasticsearchService;
import com.lawfirm.core.search.model.SearchRequest;
import com.lawfirm.core.search.model.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticsearchServiceImpl extends ElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;

    @Override
    public <T> SearchResponse<T> search(SearchRequest request, Class<T> clazz) {
        try {
            // 1. 构建查询
            Query query = buildQuery(request);
            
            // 2. 构建搜索请求
            SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(request.getIndex())
                .query(query)
                .from((request.getPage() - 1) * request.getSize())
                .size(request.getSize());

            // 3. 添加排序
            if (!request.getSorts().isEmpty()) {
                searchRequestBuilder.sort(request.getSorts().stream()
                    .map(sort -> new co.elastic.clients.elasticsearch._types.SortOptions.Builder()
                        .field(f -> f.field(sort.getField())
                            .order(sort.getDirection().equalsIgnoreCase("desc") ? 
                                co.elastic.clients.elasticsearch._types.SortOrder.Desc : 
                                co.elastic.clients.elasticsearch._types.SortOrder.Asc))
                        .build())
                    .collect(Collectors.toList()));
            }

            // 4. 添加高亮
            if (!request.getHighlightFields().isEmpty()) {
                searchRequestBuilder.highlight(h -> h
                    .fields(request.getHighlightFields().stream()
                        .collect(Collectors.toMap(
                            field -> field,
                            field -> new co.elastic.clients.elasticsearch._types.HighlightField.Builder().build()
                        ))));
            }

            // 5. 执行搜索
            SearchResponse<T> response = elasticsearchClient.search(searchRequestBuilder.build(), clazz);

            // 6. 构建返回结果
            return new SearchResponse<T>()
                .setTotal(response.hits().total().value())
                .setHits(response.hits().hits().stream()
                    .map(hit -> {
                        T source = hit.source();
                        if (hit.highlight() != null) {
                            // 处理高亮结果
                            setHighlightFields(source, hit.highlight());
                        }
                        return source;
                    })
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            log.error("Search failed", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    private Query buildQuery(SearchRequest request) {
        Query.Builder queryBuilder = new Query.Builder();

        // 1. 关键词查询
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            queryBuilder.multiMatch(m -> m
                .query(request.getKeyword())
                .fields(request.getFields().entrySet().stream()
                    .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().toString()
                    )))
                .minimumShouldMatch(request.getMinimumShouldMatch() + "%"));
        }

        // 2. 过滤条件
        if (!request.getFilters().isEmpty()) {
            queryBuilder.bool(b -> b
                .filter(request.getFilters().stream()
                    .map(filter -> {
                        Query.Builder filterBuilder = new Query.Builder();
                        switch (filter.getOperation().toLowerCase()) {
                            case "eq":
                                return filterBuilder.term(t -> t
                                    .field(filter.getField())
                                    .value(filter.getValue().toString())).build();
                            case "gt":
                                return filterBuilder.range(r -> r
                                    .field(filter.getField())
                                    .gt(filter.getValue().toString())).build();
                            case "gte":
                                return filterBuilder.range(r -> r
                                    .field(filter.getField())
                                    .gte(filter.getValue().toString())).build();
                            case "lt":
                                return filterBuilder.range(r -> r
                                    .field(filter.getField())
                                    .lt(filter.getValue().toString())).build();
                            case "lte":
                                return filterBuilder.range(r -> r
                                    .field(filter.getField())
                                    .lte(filter.getValue().toString())).build();
                            default:
                                throw new IllegalArgumentException("Unsupported operation: " + filter.getOperation());
                        }
                    })
                    .collect(Collectors.toList())));
        }

        return queryBuilder.build();
    }

    private <T> void setHighlightFields(T source, Map<String, List<String>> highlight) {
        // 通过反射设置高亮字段
        highlight.forEach((field, fragments) -> {
            try {
                java.lang.reflect.Field declaredField = source.getClass().getDeclaredField(field);
                declaredField.setAccessible(true);
                if (!fragments.isEmpty()) {
                    declaredField.set(source, String.join(" ", fragments));
                }
            } catch (Exception e) {
                log.warn("Failed to set highlight field: {}", field, e);
            }
        });
    }
} 