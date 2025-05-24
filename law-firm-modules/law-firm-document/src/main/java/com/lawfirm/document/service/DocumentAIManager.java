package com.lawfirm.document.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.model.ai.service.AiService;
import com.lawfirm.model.ai.dto.AIRequestDTO;
import com.lawfirm.model.ai.vo.AIResponseVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档AI管理器
 * 负责与core-ai模块集成，实现文档相关的AI辅助功能。
 * 包括智能摘要、标签、分类、问答、图谱等。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /**
     * 文档智能摘要
     */
    public String generateDocumentSummary(String content, Integer maxLength) {
        log.info("生成文档智能摘要，最大长度: {}", maxLength);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_SUMMARIZE");
            requestDTO.setModelName("document-summary");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("max_length", maxLength != null ? maxLength : 200);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("summary");
            } else {
                log.error("文档摘要失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("文档摘要时发生错误", e);
            return null;
        }
    }

    /**
     * 智能标签推荐
     */
    public List<String> recommendTags(String content, Integer limit) {
        log.info("文档智能标签推荐，limit: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_TAG_RECOMMEND");
            requestDTO.setModelName("document-tag");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("limit", limit != null ? limit : 5);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object tagsObj = response.getData().get("tags");
                if (tagsObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> tags = (List<String>) tagsObj;
                    return tags;
                }
            } else {
                log.error("标签推荐失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("文档标签推荐时发生错误", e);
        }
        return List.of();
    }

    /**
     * 智能分类
     */
    public Map<String, Double> classify(String content) {
        log.info("文档智能分类");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_CLASSIFY");
            requestDTO.setModelName("document-classify");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object result = response.getData().get("classification");
                if (result instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Double> map = (Map<String, Double>) result;
                    return map;
                }
            } else {
                log.error("文档分类失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("文档分类时发生错误", e);
        }
        return Map.of();
    }

    /**
     * 文档智能问答
     */
    public String documentQA(String question, String content) {
        log.info("文档智能问答: {}", question);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_QA");
            requestDTO.setModelName("document-qa");
            Map<String, Object> params = new HashMap<>();
            params.put("question", question);
            params.put("context", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("answer");
            } else {
                log.error("文档问答失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("文档问答时发生错误", e);
            return null;
        }
    }

    /**
     * 文档图谱实体关系抽取
     */
    public Map<String, Object> extractGraphRelations(String content) {
        log.info("文档图谱实体关系抽取");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_GRAPH");
            requestDTO.setModelName("document-graph");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("文档图谱关系抽取失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("文档图谱关系抽取时发生错误", e);
            return Map.of();
        }
    }

    /**
     * 文档查重/相似度分析
     */
    public List<Map<String, Object>> findSimilarDocuments(String content, Integer limit) {
        log.info("文档查重/相似度分析，limit: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_SIMILARITY");
            requestDTO.setModelName("document-similarity");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("limit", limit != null ? limit : 5);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object result = response.getData().get("similarDocuments");
                if (result instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> list = (List<Map<String, Object>>) result;
                    return list;
                }
            } else {
                log.error("文档查重/相似度分析失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("文档查重/相似度分析时发生错误", e);
        }
        return List.of();
    }

    /**
     * 文档自动生成
     */
    public String generateDocumentByTemplate(Map<String, Object> elements) {
        log.info("文档自动生成，元素: {}", elements);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_GENERATE");
            requestDTO.setModelName("document-generate");
            requestDTO.setParams(elements);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("document");
            } else {
                log.error("文档自动生成失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("文档自动生成时发生错误", e);
            return null;
        }
    }

    /**
     * 文档纠错与润色
     */
    public String proofreadAndPolish(String content) {
        log.info("文档纠错与润色");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_PROOFREAD");
            requestDTO.setModelName("document-proofread");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("polished");
            } else {
                log.error("文档纠错润色失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("文档纠错润色时发生错误", e);
            return null;
        }
    }
} 