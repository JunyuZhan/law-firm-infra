package com.lawfirm.core.search.service;

import com.lawfirm.core.search.model.SearchRequest;
import com.lawfirm.core.search.model.SearchResponse;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务接口
 */
public interface SearchService {
    
    /**
     * 创建索引
     *
     * @param index 索引名称
     * @param settings 索引设置
     * @param mappings 索引映射
     */
    void createIndex(String index, Map<String, Object> settings, Map<String, Object> mappings);
    
    /**
     * 删除索引
     *
     * @param index 索引名称
     */
    void deleteIndex(String index);
    
    /**
     * 索引是否存在
     *
     * @param index 索引名称
     * @return 是否存在
     */
    boolean indexExists(String index);
    
    /**
     * 添加文档
     *
     * @param index 索引名称
     * @param id 文档ID
     * @param document 文档内容
     */
    void addDocument(String index, String id, Object document);
    
    /**
     * 批量添加文档
     *
     * @param index 索引名称
     * @param documents 文档列表（key: 文档ID，value: 文档内容）
     */
    void addDocuments(String index, Map<String, Object> documents);
    
    /**
     * 更新文档
     *
     * @param index 索引名称
     * @param id 文档ID
     * @param document 文档内容
     */
    void updateDocument(String index, String id, Object document);
    
    /**
     * 删除文档
     *
     * @param index 索引名称
     * @param id 文档ID
     */
    void deleteDocument(String index, String id);
    
    /**
     * 批量删除文档
     *
     * @param index 索引名称
     * @param ids 文档ID列表
     */
    void deleteDocuments(String index, List<String> ids);
    
    /**
     * 获取文档
     *
     * @param index 索引名称
     * @param id 文档ID
     * @param clazz 文档类型
     * @return 文档内容
     */
    <T> T getDocument(String index, String id, Class<T> clazz);
    
    /**
     * 搜索
     *
     * @param request 搜索请求
     * @param clazz 文档类型
     * @return 搜索响应
     */
    <T> SearchResponse<T> search(SearchRequest request, Class<T> clazz);
    
    /**
     * 滚动搜索
     *
     * @param request 搜索请求
     * @param scrollId 滚动ID
     * @param scrollTimeout 滚动超时时间（如：1m）
     * @param clazz 文档类型
     * @return 搜索响应
     */
    <T> SearchResponse<T> scroll(SearchRequest request, String scrollId, String scrollTimeout, Class<T> clazz);
    
    /**
     * 清除滚动上下文
     *
     * @param scrollId 滚动ID
     */
    void clearScroll(String scrollId);
    
    /**
     * 刷新索引
     *
     * @param index 索引名称
     */
    void refreshIndex(String index);
    
    /**
     * 获取索引设置
     *
     * @param index 索引名称
     * @return 索引设置
     */
    Map<String, Object> getIndexSettings(String index);
    
    /**
     * 获取索引映射
     *
     * @param index 索引名称
     * @return 索引映射
     */
    Map<String, Object> getIndexMappings(String index);
    
    /**
     * 更新索引设置
     *
     * @param index 索引名称
     * @param settings 索引设置
     */
    void updateIndexSettings(String index, Map<String, Object> settings);
} 