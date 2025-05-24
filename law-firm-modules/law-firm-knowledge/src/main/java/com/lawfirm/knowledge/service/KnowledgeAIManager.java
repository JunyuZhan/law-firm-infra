package com.lawfirm.knowledge.service;

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
 * 知识AI管理器
 * 负责与core-ai模块集成，实现知识文档相关的AI辅助功能。
 * 包括智能摘要、标签推荐等。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KnowledgeAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /**
     * 知识文档智能摘要
     * @param content 文档内容
     * @param maxLength 最大摘要长度
     * @return 摘要内容
     */
    public String generateKnowledgeSummary(String content, Integer maxLength) {
        log.info("生成知识文档摘要，最大长度: {}", maxLength);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("KNOWLEDGE_SUMMARIZE");
            requestDTO.setModelName("knowledge-summary");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("max_length", maxLength != null ? maxLength : 200);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("summary");
            } else {
                log.error("生成知识文档摘要失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("生成知识文档摘要时发生错误", e);
            return null;
        }
    }

    /**
     * 智能标签推荐
     * @param content 文档内容
     * @param limit 推荐标签数量
     * @return 推荐标签列表
     */
    public List<String> recommendTags(String content, Integer limit) {
        log.info("知识文档智能标签推荐，limit: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("KNOWLEDGE_TAG_RECOMMEND");
            requestDTO.setModelName("knowledge-tag");
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
            log.error("知识文档标签推荐时发生错误", e);
        }
        return List.of();
    }

    /**
     * 智能问答
     * @param question 用户问题
     * @param context 文档内容或知识上下文
     * @return AI答案
     */
    public String qa(String question, String context) {
        log.info("知识智能问答: {}", question);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("KNOWLEDGE_QA");
            requestDTO.setModelName("knowledge-qa");
            Map<String, Object> params = new HashMap<>();
            params.put("question", question);
            params.put("context", context);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("answer");
            } else {
                log.error("知识问答失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("知识问答时发生错误", e);
            return null;
        }
    }

    /**
     * 智能分类
     * @param content 文档内容
     * @return 分类结果及置信度
     */
    public Map<String, Double> classify(String content) {
        log.info("知识文档智能分类");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("KNOWLEDGE_CLASSIFY");
            requestDTO.setModelName("knowledge-classify");
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
                log.error("知识文档分类失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("知识文档分类时发生错误", e);
        }
        return Map.of();
    }

    /**
     * 知识图谱关联
     * @param content 文档内容
     * @return 关联实体及关系
     */
    public Map<String, Object> extractGraphRelations(String content) {
        log.info("知识图谱实体关系抽取");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("KNOWLEDGE_GRAPH");
            requestDTO.setModelName("knowledge-graph");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("知识图谱关系抽取失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("知识图谱关系抽取时发生错误", e);
            return Map.of();
        }
    }
} 