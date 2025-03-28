package com.lawfirm.knowledge.service;

import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务集成
 * 示例如何集成core-search模块
 */
@Slf4j
@Service("knowledgeSearchService")
public class SearchIntegrationService {

    @Autowired
    @Qualifier("searchServiceImpl")
    private Object searchService;

    @Autowired
    @Qualifier("searchIndexServiceImpl")
    private Object indexService;

    /**
     * 索引知识文档
     *
     * @param knowledge 知识文档
     * @return 是否成功
     */
    public boolean indexKnowledgeDocument(Knowledge knowledge) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档为空，无法索引");
            return false;
        }

        try {
            // 构建索引文档
            Map<String, Object> document = new HashMap<>();
            document.put("id", knowledge.getId().toString());
            document.put("title", knowledge.getTitle());
            document.put("content", knowledge.getContent());
            document.put("summary", knowledge.getSummary());
            document.put("keywords", knowledge.getKeywords());
            document.put("categoryId", knowledge.getCategoryId());
            document.put("knowledgeType", knowledge.getKnowledgeType() != null ? knowledge.getKnowledgeType().getCode() : null);
            document.put("authorName", knowledge.getAuthorName());
            document.put("createTime", knowledge.getCreateTime());
            document.put("updateTime", knowledge.getUpdateTime());

            // 索引文档
            ((IndexDocumentFunction) indexService).indexDocument("knowledge", knowledge.getId().toString(), document);
            log.info("知识文档索引成功: id={}, title={}", knowledge.getId(), knowledge.getTitle());
            return true;
        } catch (Exception e) {
            log.error("知识文档索引失败: id={}, title={}, error={}", 
                    knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除知识文档索引
     *
     * @param knowledgeId 知识ID
     * @return 是否成功
     */
    public boolean deleteKnowledgeIndex(Long knowledgeId) {
        if (knowledgeId == null) {
            log.error("知识ID为空，无法删除索引");
            return false;
        }

        try {
            // 删除索引
            ((DeleteDocumentFunction) indexService).deleteDocument("knowledge", knowledgeId.toString());
            log.info("知识文档索引删除成功: id={}", knowledgeId);
            return true;
        } catch (Exception e) {
            log.error("知识文档索引删除失败: id={}, error={}", knowledgeId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 搜索知识文档
     *
     * @param keyword 关键词
     * @param limit 限制结果数量
     * @return 知识ID列表
     */
    public List<Long> searchKnowledge(String keyword, int limit) {
        if (keyword == null || keyword.trim().isEmpty()) {
            log.error("搜索关键词为空");
            return new ArrayList<>();
        }

        try {
            // 构建搜索条件
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("keyword", keyword);
            searchParams.put("fields", new String[]{"title", "content", "summary", "keywords"});
            searchParams.put("limit", limit);

            // 执行搜索
            List<Map<String, Object>> results = ((SearchDocumentsFunction) searchService)
                    .searchDocuments("knowledge", searchParams);

            // 提取知识ID
            List<Long> knowledgeIds = new ArrayList<>();
            for (Map<String, Object> result : results) {
                if (result.containsKey("id")) {
                    knowledgeIds.add(Long.valueOf(result.get("id").toString()));
                }
            }

            log.info("知识文档搜索完成: keyword={}, resultCount={}", keyword, knowledgeIds.size());
            return knowledgeIds;
        } catch (Exception e) {
            log.error("知识文档搜索失败: keyword={}, error={}", keyword, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 索引文档函数接口
     */
    private interface IndexDocumentFunction {
        void indexDocument(String indexName, String id, Map<String, Object> document);
    }

    /**
     * 删除文档函数接口
     */
    private interface DeleteDocumentFunction {
        void deleteDocument(String indexName, String id);
    }

    /**
     * 搜索文档函数接口
     */
    private interface SearchDocumentsFunction {
        List<Map<String, Object>> searchDocuments(String indexName, Map<String, Object> searchParams);
    }
} 