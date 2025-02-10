package com.lawfirm.document.service;

import com.lawfirm.core.search.service.BaseSearchService;
import com.lawfirm.model.document.index.DocumentIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 文档搜索服务接口
 */
public interface IDocumentSearchService extends BaseSearchService<DocumentIndex, Long> {
    
    /**
     * 创建或更新文档索引
     */
    void createOrUpdateIndex(DocumentIndex documentIndex);
    
    /**
     * 批量创建或更新文档索引
     */
    void createOrUpdateIndexBatch(List<DocumentIndex> documentIndices);
    
    /**
     * 删除文档索引
     */
    void deleteIndex(Long documentId);
    
    /**
     * 批量删除文档索引
     */
    void deleteIndexBatch(List<Long> documentIds);
    
    /**
     * 全文搜索
     */
    Page<DocumentIndex> search(String keyword, Pageable pageable);
    
    /**
     * 高级搜索
     */
    Page<DocumentIndex> advancedSearch(Map<String, Object> searchParams, Pageable pageable);
    
    /**
     * 相似文档推荐
     */
    List<DocumentIndex> findSimilar(Long documentId, int maxResults);
    
    /**
     * 按标签搜索
     */
    Page<DocumentIndex> searchByTags(List<String> tags, Pageable pageable);
    
    /**
     * 搜索建议
     */
    List<String> suggest(String prefix, int maxSuggestions);
    
    /**
     * 重建索引
     */
    void rebuildIndex();
    
    /**
     * 获取索引统计信息
     */
    Map<String, Object> getIndexStats();
} 