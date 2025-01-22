package com.lawfirm.common.data.search;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * 搜索服务接口
 */
public interface SearchService<T> {
    /**
     * 保存文档
     */
    T save(T document);

    /**
     * 批量保存文档
     */
    Iterable<T> saveAll(Iterable<T> documents);

    /**
     * 删除文档
     */
    void delete(String id);

    /**
     * 搜索文档
     */
    SearchHits<T> search(SearchTemplate searchTemplate);

    /**
     * 分页搜索文档
     */
    Page<T> searchPage(SearchTemplate searchTemplate);
} 