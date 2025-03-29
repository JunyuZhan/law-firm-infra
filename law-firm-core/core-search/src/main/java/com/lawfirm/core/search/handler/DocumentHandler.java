package com.lawfirm.core.search.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文档处理接口
 * 处理Lucene索引中的文档操作
 */
public interface DocumentHandler {
    
    /**
     * 索引单个文档
     *
     * @param indexName 索引名称
     * @param id 文档ID
     * @param document 文档内容
     * @throws IOException 如果处理过程中发生IO错误
     */
    void indexDoc(String indexName, String id, Map<String, Object> document) throws IOException;
    
    /**
     * 批量索引文档
     *
     * @param indexName 索引名称
     * @param documents 文档列表
     * @throws IOException 如果处理过程中发生IO错误
     */
    void bulkIndex(String indexName, List<Map<String, Object>> documents) throws IOException;
    
    /**
     * 更新文档
     *
     * @param indexName 索引名称
     * @param id 文档ID
     * @param document 文档内容
     * @throws IOException 如果处理过程中发生IO错误
     */
    void updateDoc(String indexName, String id, Map<String, Object> document) throws IOException;
    
    /**
     * 删除文档
     *
     * @param indexName 索引名称
     * @param id 文档ID
     * @throws IOException 如果处理过程中发生IO错误
     */
    void deleteDoc(String indexName, String id) throws IOException;
    
    /**
     * 批量删除文档
     *
     * @param indexName 索引名称
     * @param ids 文档ID列表
     * @throws IOException 如果处理过程中发生IO错误
     */
    void bulkDelete(String indexName, List<String> ids) throws IOException;
    
    /**
     * 获取文档
     *
     * @param indexName 索引名称
     * @param id 文档ID
     * @return 文档内容
     * @throws IOException 如果处理过程中发生IO错误
     */
    Map<String, Object> getDoc(String indexName, String id) throws IOException;
    
    /**
     * 批量获取文档
     *
     * @param indexName 索引名称
     * @param ids 文档ID列表
     * @return 文档内容列表
     * @throws IOException 如果处理过程中发生IO错误
     */
    List<Map<String, Object>> multiGet(String indexName, List<String> ids) throws IOException;
    
    /**
     * 判断文档是否存在
     *
     * @param indexName 索引名称
     * @param id 文档ID
     * @return 存在返回true，否则返回false
     * @throws IOException 如果处理过程中发生IO错误
     */
    boolean existsDoc(String indexName, String id) throws IOException;
} 