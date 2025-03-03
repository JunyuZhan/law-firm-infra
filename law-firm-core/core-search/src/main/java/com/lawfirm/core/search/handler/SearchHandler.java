package com.lawfirm.core.search.handler;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.lawfirm.core.search.utils.QueryBuilderUtils;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.vo.SearchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ES搜索处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SearchHandler {

    private final ElasticsearchClient client;

    /**
     * 执行搜索
     */
    @SuppressWarnings("unchecked")
    public SearchVO search(SearchRequestDTO request) throws IOException {
        // 构建搜索请求
        SearchRequest searchRequest = SearchRequest.of(builder -> {
            // 设置索引
            builder.index(request.getIndexName());
            
            // 设置查询条件
            if (request.getKeyword() != null) {
                builder.query(QueryBuilderUtils.buildMultiMatchQuery(request.getKeyword(), request.getFields()));
            }
            
            // 设置过滤条件
            if (request.getFilters() != null) {
                builder.postFilter(QueryBuilderUtils.buildFilterQuery(request.getFilters()));
            }
            
            // 设置排序
            if (request.getSorts() != null) {
                request.getSorts().forEach((field, order) -> 
                    builder.sort(s -> s.field(f -> f.field(field).order(SortOrder.valueOf(order.toUpperCase()))))
                );
            }
            
            // 设置分页
            builder.from(request.getPageNum() * request.getPageSize())
                   .size(request.getPageSize());
            
            // 设置高亮
            if (!request.getHighlights().isEmpty()) {
                builder.highlight(h -> {
                    request.getHighlights().forEach(field -> 
                        h.fields(field, f -> f)
                    );
                    return h;
                });
            }
            
            // 设置聚合
            if (request.getAggregations() != null) {
                request.getAggregations().forEach((name, config) -> 
                    builder.aggregations(name, a -> a.terms(t -> t.field(config.toString())))
                );
            }
            
            // 设置源字段过滤
            if (!request.getIncludes().isEmpty() || !request.getExcludes().isEmpty()) {
                builder.source(s -> s
                    .filter(f -> f
                        .includes(request.getIncludes())
                        .excludes(request.getExcludes())
                    )
                );
            }
            
            // 设置最小得分
            if (request.getMinScore() != null) {
                builder.minScore(request.getMinScore().doubleValue());
            }
            
            return builder;
        });

        // 执行搜索
        SearchResponse<JsonData> response = client.search(searchRequest, JsonData.class);
        
        // 构建返回结果
        SearchVO result = new SearchVO()
            .setTotal(response.hits().total().value())
            .setMaxScore(response.hits().maxScore() != null ? response.hits().maxScore().floatValue() : null)
            .setTimedOut(response.timedOut())
            .setTook(response.took());

        // 处理命中记录
        List<SearchVO.Hit> hits = response.hits().hits().stream()
            .map(this::convertHit)
            .collect(Collectors.toList());
        result.setHits(hits);

        // 处理聚合结果
        if (response.aggregations() != null) {
            Map<String, Object> aggregations = new HashMap<>();
            response.aggregations().forEach((name, agg) -> 
                aggregations.put(name, convertAggregation(agg))
            );
            result.setAggregations(aggregations);
        }

        return result;
    }

    /**
     * 转换命中记录
     */
    @SuppressWarnings("unchecked")
    private SearchVO.Hit convertHit(Hit<JsonData> hit) {
        SearchVO.Hit result = new SearchVO.Hit()
            .setIndex(hit.index())
            .setId(hit.id())
            .setScore(hit.score() != null ? hit.score().floatValue() : null)
            .setSource(hit.source() != null ? hit.source().to(Map.class) : null);

        if (hit.highlight() != null) {
            result.setHighlight(hit.highlight());
        }

        if (hit.sort() != null) {
            result.setSort(hit.sort().stream()
                .map(value -> value != null ? value.toString() : null)
                .collect(Collectors.toList()));
        }

        if (hit.explanation() != null) {
            Map<String, Object> explanation = new HashMap<>();
            explanation.put("value", hit.explanation().value());
            explanation.put("description", hit.explanation().description());
            explanation.put("details", hit.explanation().details().stream()
                .map(detail -> {
                    Map<String, Object> detailMap = new HashMap<>();
                    detailMap.put("value", detail.value());
                    detailMap.put("description", detail.description());
                    return detailMap;
                })
                .collect(Collectors.toList()));
            result.setExplanation(explanation);
        }

        return result;
    }

    /**
     * 转换聚合结果
     */
    private Object convertAggregation(Aggregate agg) {
        if (agg.sterms() != null) {
            return agg.sterms().buckets().array().stream()
                .map(bucket -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("key", bucket.key());
                    result.put("docCount", bucket.docCount());
                    return result;
                })
                .collect(Collectors.toList());
        }
        return agg;
    }

    /**
     * 获取搜索建议
     */
    public List<String> suggest(String indexName, String field, String text) throws IOException {
        SearchRequest request = SearchRequest.of(builder ->
            builder.index(indexName)
                   .suggest(s -> s
                       .suggesters("my-suggest", sg -> sg
                           .text(text)
                           .completion(c -> c
                               .field(field)
                               .skipDuplicates(true)
                               .size(5)
                           )
                       )
                   )
        );

        SearchResponse<JsonData> response = client.search(request, JsonData.class);
        
        return response.suggest().get("my-suggest").stream()
            .flatMap(suggestion -> suggestion.completion().options().stream())
            .map(option -> option.text())
            .collect(Collectors.toList());
    }
}