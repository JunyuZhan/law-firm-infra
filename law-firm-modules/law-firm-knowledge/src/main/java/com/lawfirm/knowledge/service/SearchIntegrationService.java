package com.lawfirm.knowledge.service;

import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import com.lawfirm.core.search.handler.IndexHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 搜索服务集成
 * 集成core-search模块
 */
@Slf4j
@Service("knowledgeSearchService")
public class SearchIntegrationService {

    private static final String INDEX_NAME = "knowledge";

    @Autowired
    @Qualifier("coreSearchServiceImpl")
    private SearchService searchService;

    @Autowired
    @Qualifier("luceneIndexHandler")
    private IndexHandler indexHandler;

    /**
     * 索引知识文档
     *
     * @param knowledge 知识文档
     * @return 是否成功
     */
    public boolean indexKnowledgeDocument(Knowledge knowledge) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档为空或ID为空，无法创建索引");
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
            document.put("categoryId", knowledge.getCategoryId() != null ? knowledge.getCategoryId().toString() : "");
            document.put("type", knowledge.getKnowledgeType() != null ? knowledge.getKnowledgeType().name() : "");
            document.put("status", knowledge.getStatus() != null ? knowledge.getStatus().toString() : "0");
            document.put("createTime", knowledge.getCreateTime() != null ? knowledge.getCreateTime().toString() : "");

            // 添加到索引
            searchService.indexDoc(INDEX_NAME, knowledge.getId().toString(), document);
            log.info("知识文档索引创建成功: id={}, title={}", knowledge.getId(), knowledge.getTitle());
            return true;
        } catch (Exception e) {
            log.error("知识文档索引创建失败: id={}, title={}, error={}", 
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
            searchService.deleteDoc(INDEX_NAME, knowledgeId.toString());
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
            // 创建搜索请求
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(INDEX_NAME);
            request.setKeyword(keyword);
            request.setFields(List.of("title", "content", "summary", "keywords"));
            request.setPageNum(1);
            request.setPageSize(limit > 0 ? limit : 50);
            
            // 执行搜索
            SearchVO result = searchService.search(request);
            
            // 提取知识ID
            List<Long> knowledgeIds = new ArrayList<>();
            if (result != null && result.getHits() != null) {
                knowledgeIds = result.getHits().stream()
                    .filter(hit -> hit.getSource() != null && hit.getSource().containsKey("id"))
                    .map(hit -> Long.valueOf(hit.getSource().get("id").toString()))
                    .collect(Collectors.toList());
            }

            log.info("知识文档搜索完成: keyword={}, resultCount={}", keyword, knowledgeIds.size());
            return knowledgeIds;
        } catch (Exception e) {
            log.error("知识文档搜索失败: keyword={}, error={}", keyword, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
} 