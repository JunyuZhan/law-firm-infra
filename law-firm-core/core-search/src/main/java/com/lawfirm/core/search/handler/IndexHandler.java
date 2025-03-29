package com.lawfirm.core.search.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;

/**
 * 索引处理接口
 * Lucene实现版本，替代Elasticsearch
 */
public interface IndexHandler {

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @param mapping 索引映射
     * @throws IOException IO异常
     */
    void createIndex(String indexName, Object mapping) throws IOException;

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    void deleteIndex(String indexName) throws IOException;

    /**
     * 更新索引设置
     *
     * @param indexName 索引名称
     * @param settings 索引设置
     * @throws IOException IO异常
     */
    void updateSettings(String indexName, String settings) throws IOException;

    /**
     * 更新索引映射
     *
     * @param indexName 索引名称
     * @param mapping 索引映射
     * @throws IOException IO异常
     */
    void updateMapping(String indexName, Object mapping) throws IOException;

    /**
     * 获取索引信息
     *
     * @param indexName 索引名称
     * @return 索引信息
     * @throws IOException IO异常
     */
    Object getIndex(String indexName) throws IOException;

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return 是否存在
     * @throws IOException IO异常
     */
    boolean existsIndex(String indexName) throws IOException;

    /**
     * 打开索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    void openIndex(String indexName) throws IOException;

    /**
     * 关闭索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    void closeIndex(String indexName) throws IOException;

    /**
     * 刷新索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    void refreshIndex(String indexName) throws IOException;

    /**
     * 创建别名
     *
     * @param indexName 索引名称
     * @param alias 别名
     * @throws IOException IO异常
     */
    void createAlias(String indexName, String alias) throws IOException;

    /**
     * 删除别名
     *
     * @param indexName 索引名称
     * @param alias 别名
     * @throws IOException IO异常
     */
    void deleteAlias(String indexName, String alias) throws IOException;
    
    /**
     * 添加文档
     * 
     * @param indexName 索引名称
     * @param document 文档
     * @throws IOException IO异常
     */
    void addDocument(String indexName, Document document) throws IOException;
    
    /**
     * 批量添加文档
     * 
     * @param indexName 索引名称
     * @param documents 文档列表
     * @throws IOException IO异常
     */
    void addDocuments(String indexName, List<Document> documents) throws IOException;
    
    /**
     * 更新文档
     * 
     * @param indexName 索引名称
     * @param term 条件
     * @param document 文档
     * @throws IOException IO异常
     */
    void updateDocument(String indexName, Object term, Document document) throws IOException;
    
    /**
     * 删除文档
     * 
     * @param indexName 索引名称
     * @param query 查询条件
     * @throws IOException IO异常
     */
    void deleteDocument(String indexName, Object query) throws IOException;
    
    /**
     * 删除所有文档
     * 
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    void deleteAllDocuments(String indexName) throws IOException;
    
    /**
     * 搜索文档
     * 
     * @param indexName 索引名称
     * @param query 查询条件
     * @param limit 限制数量
     * @return 搜索结果
     * @throws IOException IO异常
     */
    Object search(String indexName, Object query, int limit) throws IOException;
    
    /**
     * 搜索文档（带排序）
     * 
     * @param indexName 索引名称
     * @param query 查询条件
     * @param sort 排序
     * @param limit 限制数量
     * @return 搜索结果
     * @throws IOException IO异常
     */
    Object search(String indexName, Object query, Object sort, int limit) throws IOException;
    
    /**
     * 搜索文档并返回Document列表
     * 
     * @param indexName 索引名称
     * @param query 查询条件
     * @param limit 限制数量
     * @return 文档列表
     * @throws IOException IO异常
     */
    List<Document> searchDocuments(String indexName, Object query, int limit) throws IOException;
    
    /**
     * 获取Lucene分析器
     * 
     * @return 分析器
     */
    Object getAnalyzer();
} 