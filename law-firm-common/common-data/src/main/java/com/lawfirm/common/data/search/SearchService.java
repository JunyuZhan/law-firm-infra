package com.lawfirm.common.data.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.io.IOException;
import java.util.List;

/**
 * 搜索服务接口
 */
public interface SearchService {

    /**
     * 执行搜索
     *
     * @param index 索引名称
     * @param query 查询条件
     * @param clazz 返回类型
     * @return 搜索结果
     */
    <T> List<T> search(String index, Query query, Class<T> clazz) throws IOException;
} 