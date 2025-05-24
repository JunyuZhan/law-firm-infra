package com.lawfirm.contract.service;

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
 * 合同AI管理器
 * 负责与core-ai模块集成，实现合同相关的AI辅助功能。
 * 包括智能摘要、风险识别、条款推荐、合同问答等。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /**
     * 合同智能摘要
     */
    public String generateContractSummary(String content, Integer maxLength) {
        log.info("生成合同智能摘要，最大长度: {}", maxLength);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_SUMMARIZE");
            requestDTO.setModelName("contract-summary");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("max_length", maxLength != null ? maxLength : 200);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("summary");
            } else {
                log.error("合同摘要失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("合同摘要时发生错误", e);
            return null;
        }
    }

    /**
     * 合同风险识别
     */
    public Map<String, Object> detectContractRisks(String content) {
        log.info("合同风险识别");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_RISK");
            requestDTO.setModelName("contract-risk");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("合同风险识别失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("合同风险识别时发生错误", e);
            return Map.of();
        }
    }

    /**
     * 智能条款推荐
     */
    public List<String> recommendContractClauses(String content, Integer limit) {
        log.info("合同智能条款推荐，limit: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_CLAUSE_RECOMMEND");
            requestDTO.setModelName("contract-clause");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("limit", limit != null ? limit : 5);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object clausesObj = response.getData().get("clauses");
                if (clausesObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> clauses = (List<String>) clausesObj;
                    return clauses;
                }
            } else {
                log.error("条款推荐失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("合同条款推荐时发生错误", e);
        }
        return List.of();
    }

    /**
     * 合同智能问答
     */
    public String contractQA(String question, String content) {
        log.info("合同智能问答: {}", question);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_QA");
            requestDTO.setModelName("contract-qa");
            Map<String, Object> params = new HashMap<>();
            params.put("question", question);
            params.put("context", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("answer");
            } else {
                log.error("合同问答失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("合同问答时发生错误", e);
            return null;
        }
    }

    /**
     * 合同查重/相似度分析
     */
    public List<Map<String, Object>> findSimilarContracts(String content, Integer limit) {
        log.info("合同查重/相似度分析，limit: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_SIMILARITY");
            requestDTO.setModelName("contract-similarity");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            params.put("limit", limit != null ? limit : 5);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object result = response.getData().get("similarContracts");
                if (result instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> list = (List<Map<String, Object>>) result;
                    return list;
                }
            } else {
                log.error("合同查重/相似度分析失败: {}", response != null ? response.getMessage() : "无响应");
            }
        } catch (Exception e) {
            log.error("合同查重/相似度分析时发生错误", e);
        }
        return List.of();
    }

    /**
     * 合同自动生成
     */
    public String generateContractByTemplate(Map<String, Object> elements) {
        log.info("合同自动生成，元素: {}", elements);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_GENERATE");
            requestDTO.setModelName("contract-generate");
            requestDTO.setParams(elements);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("contract");
            } else {
                log.error("合同自动生成失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("合同自动生成时发生错误", e);
            return null;
        }
    }

    /**
     * 合同纠错与润色
     */
    public String proofreadAndPolish(String content) {
        log.info("合同纠错与润色");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CONTRACT_PROOFREAD");
            requestDTO.setModelName("contract-proofread");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("polished");
            } else {
                log.error("合同纠错润色失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("合同纠错润色时发生错误", e);
            return null;
        }
    }
} 