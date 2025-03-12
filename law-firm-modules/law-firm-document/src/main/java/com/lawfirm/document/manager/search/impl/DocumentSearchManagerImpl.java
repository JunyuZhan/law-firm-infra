package com.lawfirm.document.manager.search.impl;

import com.lawfirm.document.manager.search.DocumentSearchManager;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.vo.SearchVO;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档搜索管理器实现类
 * 整合搜索、索引管理和优化功能
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentSearchManagerImpl implements DocumentSearchManager {

    private final SearchService searchService;
    private final AiService aiService;

    @Override
    public List<DocumentVO> search(String keyword) {
        // 1. 构建搜索请求
        SearchRequestDTO request = buildSearchRequest(keyword, 1, 20);

        // 2. 执行搜索
        SearchVO result = searchService.search(request);

        // 3. 转换为文档列表
        return result.getDocuments().stream()
            .map(doc -> DocumentVO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .content(doc.getContent())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public List<DocumentVO> recommend(Long documentId) {
        // 1. 获取文档内容
        DocumentVO document = searchService.getDocument(documentId);
        if (document == null) {
            return List.of();
        }

        // 2. 基于内容推荐相关文档
        SearchRequestDTO request = buildRecommendRequest(document.getContent());
        SearchVO result = searchService.search(request);

        // 3. 转换为文档列表
        return result.getDocuments().stream()
            .filter(doc -> !doc.getId().equals(documentId))
            .map(doc -> DocumentVO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .content(doc.getContent())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public String generateSummary(Long documentId) {
        // 1. 获取文档内容
        DocumentVO document = searchService.getDocument(documentId);
        if (document == null) {
            return null;
        }

        // 2. 使用AI服务生成摘要
        return aiService.generateSummary(document.getContent());
    }

    @Override
    public Map<String, Object> extractKeyInfo(Long documentId) {
        // 1. 获取文档内容
        DocumentVO document = searchService.getDocument(documentId);
        if (document == null) {
            return Map.of();
        }

        // 2. 使用AI服务提取关键信息
        return aiService.extractKeyInfo(document.getContent());
    }

    /**
     * 构建搜索请求
     */
    private SearchRequestDTO buildSearchRequest(String keyword, Integer pageNum, Integer pageSize) {
        SearchRequestDTO request = new SearchRequestDTO();
        request.setKeyword(keyword);
        request.setPageNum(pageNum);
        request.setPageSize(pageSize);
        return request;
    }

    /**
     * 构建推荐请求
     */
    private SearchRequestDTO buildRecommendRequest(String content) {
        SearchRequestDTO request = new SearchRequestDTO();
        request.setKeyword(content);
        request.setPageNum(1);
        request.setPageSize(10);
        request.setUseSemanticSearch(true);
        return request;
    }
} 