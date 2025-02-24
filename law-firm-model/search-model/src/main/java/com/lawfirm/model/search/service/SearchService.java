package com.lawfirm.model.search.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.vo.SearchVO;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务接口
 */
public interface SearchService extends BaseService<SearchDoc> {

    /**
     * 搜索
     */
    SearchVO search(SearchRequestDTO request);

    /**
     * 批量索引文档
     */
    void bulkIndex(String indexName, List<Map<String, Object>> documents);

    /**
     * 索引单个文档
     */
    void indexDoc(String indexName, String id, Map<String, Object> document);

    /**
     * 更新文档
     */
    void updateDoc(String indexName, String id, Map<String, Object> document);

    /**
     * 删除文档
     */
    void deleteDoc(String indexName, String id);

    /**
     * 批量删除文档
     */
    void bulkDelete(String indexName, List<String> ids);

    /**
     * 获取文档
     */
    Map<String, Object> getDoc(String indexName, String id);

    /**
     * 批量获取文档
     */
    List<Map<String, Object>> multiGet(String indexName, List<String> ids);

    /**
     * 文档是否存在
     */
    boolean existsDoc(String indexName, String id);

    /**
     * 获取文档数量
     */
    long count(String indexName, Map<String, Object> query);

    /**
     * 清空索引数据
     */
    void clearIndex(String indexName);

    /**
     * 分析文本
     */
    List<String> analyze(String indexName, String analyzer, String text);

    /**
     * 获取建议
     */
    List<String> suggest(String indexName, String field, String text);
} 