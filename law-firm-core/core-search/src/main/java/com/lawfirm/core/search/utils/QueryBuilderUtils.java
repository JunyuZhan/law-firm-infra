package com.lawfirm.core.search.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

/**
 * 查询构建工具类
 */
@UtilityClass
public class QueryBuilderUtils {

    /**
     * 构建多字段匹配查询
     */
    public Query buildMultiMatchQuery(String keyword, List<String> fields) {
        Assert.hasText(keyword, "关键字不能为空");
        Assert.notEmpty(fields, "搜索字段不能为空");
        
        return Query.of(q -> 
            q.multiMatch(m -> 
                m.query(keyword)
                 .fields(fields)
            )
        );
    }

    /**
     * 构建词条查询
     */
    public Query buildTermQuery(String field, Object value) {
        Assert.hasText(field, "字段不能为空");
        Assert.notNull(value, "值不能为空");
        
        return Query.of(q -> 
            q.term(t -> 
                t.field(field)
                 .value(convertToFieldValue(value))
            )
        );
    }

    /**
     * 构建范围查询
     */
    public Query buildRangeQuery(String field, Object from, Object to) {
        Assert.hasText(field, "字段不能为空");
        
        return Query.of(q -> 
            q.range(r -> {
                r.field(field);
                if (from != null) {
                    r.gte(JsonData.of(from));
                }
                if (to != null) {
                    r.lte(JsonData.of(to));
                }
                return r;
            })
        );
    }

    /**
     * 构建布尔查询
     */
    public Query buildBoolQuery(List<Query> must, List<Query> should, List<Query> mustNot, List<Query> filter) {
        return Query.of(q -> 
            q.bool(b -> {
                if (!CollectionUtils.isEmpty(must)) {
                    b.must(must);
                }
                if (!CollectionUtils.isEmpty(should)) {
                    b.should(should);
                }
                if (!CollectionUtils.isEmpty(mustNot)) {
                    b.mustNot(mustNot);
                }
                if (!CollectionUtils.isEmpty(filter)) {
                    b.filter(filter);
                }
                return b;
            })
        );
    }

    /**
     * 构建过滤查询
     */
    public Query buildFilterQuery(Map<String, Object> filters) {
        Assert.notEmpty(filters, "过滤条件不能为空");
        
        return Query.of(q -> 
            q.bool(b -> {
                filters.forEach((field, value) -> 
                    b.must(m -> m.term(t -> 
                        t.field(field)
                         .value(convertToFieldValue(value))
                    ))
                );
                return b;
            })
        );
    }

    /**
     * 构建模糊查询
     */
    public Query buildFuzzyQuery(String field, String value) {
        Assert.hasText(field, "字段不能为空");
        Assert.hasText(value, "值不能为空");
        
        return Query.of(q -> 
            q.fuzzy(f -> 
                f.field(field)
                 .value(value)
                 .fuzziness("AUTO")
            )
        );
    }

    /**
     * 构建前缀查询
     */
    public Query buildPrefixQuery(String field, String prefix) {
        Assert.hasText(field, "字段不能为空");
        Assert.hasText(prefix, "前缀不能为空");
        
        return Query.of(q -> 
            q.prefix(p -> 
                p.field(field)
                 .value(prefix)
            )
        );
    }

    /**
     * 构建通配符查询
     */
    public Query buildWildcardQuery(String field, String wildcard) {
        Assert.hasText(field, "字段不能为空");
        Assert.hasText(wildcard, "通配符不能为空");
        
        return Query.of(q -> 
            q.wildcard(w -> 
                w.field(field)
                 .value(wildcard)
            )
        );
    }

    /**
     * 将Object转换为FieldValue
     */
    private FieldValue convertToFieldValue(Object value) {
        if (value == null) {
            return FieldValue.NULL;
        }
        if (value instanceof String) {
            return FieldValue.of((String) value);
        }
        if (value instanceof Long) {
            return FieldValue.of((Long) value);
        }
        if (value instanceof Integer) {
            return FieldValue.of((Integer) value);
        }
        if (value instanceof Double) {
            return FieldValue.of((Double) value);
        }
        if (value instanceof Float) {
            return FieldValue.of((Float) value);
        }
        if (value instanceof Boolean) {
            return FieldValue.of((Boolean) value);
        }
        return FieldValue.of(value.toString());
    }
} 