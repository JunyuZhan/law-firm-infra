package com.lawfirm.core.search.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询构建工具类
 */
public class QueryBuilderUtils {
    
    /**
     * 构建多字段匹配查询
     */
    public static Query buildMultiMatchQuery(String keyword, List<String> fields) {
        if (keyword == null || fields == null || fields.isEmpty()) {
            return null;
        }
        
        return Query.of(q -> q.multiMatch(m -> 
            m.query(keyword)
             .fields(fields)
             .minimumShouldMatch("75%")
        ));
    }

    /**
     * 构建过滤查询
     */
    public static Query buildFilterQuery(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();
        
        filters.forEach((field, value) -> {
            if (value != null) {
                if (value instanceof List) {
                    List<FieldValue> fieldValues = ((List<?>) value).stream()
                        .map(QueryBuilderUtils::toFieldValue)
                        .collect(Collectors.toList());
                    
                    boolBuilder.filter(Query.of(q -> 
                        q.terms(t -> t.field(field)
                                    .terms(t2 -> t2.value(fieldValues)))
                    ));
                } else {
                    boolBuilder.filter(Query.of(q -> 
                        q.term(t -> t.field(field)
                                   .value(toFieldValue(value)))
                    ));
                }
            }
        });
        
        return Query.of(q -> q.bool(boolBuilder.build()));
    }
    
    /**
     * 将对象转换为 FieldValue
     */
    private static FieldValue toFieldValue(Object value) {
        if (value instanceof String) {
            return FieldValue.of((String) value);
        } else if (value instanceof Long) {
            return FieldValue.of((Long) value);
        } else if (value instanceof Integer) {
            return FieldValue.of((Integer) value);
        } else if (value instanceof Double) {
            return FieldValue.of((Double) value);
        } else if (value instanceof Float) {
            return FieldValue.of((Float) value);
        } else if (value instanceof Boolean) {
            return FieldValue.of((Boolean) value);
        }
        return FieldValue.of(value.toString());
    }
} 