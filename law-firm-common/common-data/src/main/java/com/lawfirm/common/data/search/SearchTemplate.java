package com.lawfirm.common.data.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

/**
 * 搜索模板
 */
public class SearchTemplate {
    private final NativeSearchQueryBuilder queryBuilder;
    private final Pageable pageable;

    public SearchTemplate(Pageable pageable) {
        this.queryBuilder = new NativeSearchQueryBuilder();
        this.pageable = pageable;
    }

    public NativeSearchQuery buildQuery() {
        return queryBuilder.withPageable(pageable).build();
    }

    public Pageable getPageable() {
        return pageable;
    }

    public NativeSearchQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
} 