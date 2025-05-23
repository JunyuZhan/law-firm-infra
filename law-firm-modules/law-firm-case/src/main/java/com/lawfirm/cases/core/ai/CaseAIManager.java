package com.lawfirm.cases.core.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.lawfirm.model.ai.service.AiService;
import com.lawfirm.model.ai.dto.AIRequestDTO;
import com.lawfirm.model.ai.vo.AIResponseVO;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件AI管理器
 * <p>
 * 负责与core-ai模块集成，实现案件相关的AI辅助功能。
 * 包括案件文档摘要、文本分析、风险识别和智能建议等。
 * </p>
 *
 * @author JunyuZhan
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;
    
    /**
     * 获取案件文档摘要
     *
     * @param documentContent 文档内容
     * @param maxLength 最大摘要长度
     * @return 摘要内容
     */
    public String generateDocumentSummary(String documentContent, Integer maxLength) {
        log.info("生成案件文档摘要，最大长度: {}", maxLength);
        
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("SUMMARIZE");
            requestDTO.setModelName("text-summary");
            
            Map<String, Object> params = new HashMap<>();
            params.put("text", documentContent);
            params.put("max_length", maxLength != null ? maxLength : 200);
            requestDTO.setParams(params);
            
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("summary");
            } else {
                log.error("生成摘要失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("生成案件文档摘要时发生错误", e);
            return null;
        }
    }
    
    /**
     * 提取案件关键要素
     *
     * @param caseContent 案件内容
     * @return 关键要素
     */
    public Map<String, Object> extractCaseKeyElements(String caseContent) {
        log.info("提取案件关键要素");
        
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("EXTRACT_ELEMENTS");
            requestDTO.setModelName("legal-ner");
            
            Map<String, Object> params = new HashMap<>();
            params.put("text", caseContent);
            params.put("elements", List.of("当事人", "案由", "请求", "事实", "争议焦点", "法律依据", "判决结果"));
            requestDTO.setParams(params);
            
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("提取案件关键要素失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("提取案件关键要素时发生错误", e);
            return Map.of();
        }
    }
    
    /**
     * 获取案件风险评估
     *
     * @param caseId 案件ID
     * @param caseData 案件数据
     * @return 风险评估结果
     */
    public Map<String, Object> getCaseRiskAssessment(Long caseId, Map<String, Object> caseData) {
        log.info("获取案件风险评估, 案件ID: {}", caseId);
        
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("RISK_ASSESSMENT");
            requestDTO.setModelName("legal-risk");
            
            Map<String, Object> params = new HashMap<>(caseData);
            params.put("case_id", caseId);
            requestDTO.setParams(params);
            
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("获取案件风险评估失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("获取案件风险评估时发生错误", e);
            return Map.of();
        }
    }
    
    /**
     * 获取类似案例推荐
     *
     * @param caseDescription 案件描述
     * @param limit 推荐数量
     * @return 推荐案例列表
     */
    public List<Map<String, Object>> getSimilarCaseRecommendations(String caseDescription, Integer limit) {
        log.info("获取类似案例推荐, 推荐数量: {}", limit);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("SIMILAR_CASES");
            requestDTO.setModelName("case-similarity");
            Map<String, Object> params = new HashMap<>();
            params.put("description", caseDescription);
            params.put("limit", limit != null ? limit : 5);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                Object casesObj = response.getData().get("cases");
                if (casesObj instanceof List) {
                    List<?> tempList = (List<?>) casesObj;
                    if (tempList.isEmpty() || tempList.get(0) instanceof Map) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> result = (List<Map<String, Object>>) casesObj;
                        return result;
                    }
                }
                log.warn("AI返回的cases类型不匹配: {}", casesObj != null ? casesObj.getClass() : null);
                return List.of();
            } else {
                log.error("获取类似案例推荐失败: {}", response != null ? response.getMessage() : "无响应");
                return List.of();
            }
        } catch (Exception e) {
            log.error("获取类似案例推荐时发生错误", e);
            return List.of();
        }
    }
    
    /**
     * 生成法律文档建议
     *
     * @param documentType 文档类型
     * @param caseInfo 案件信息
     * @return 文档建议
     */
    public String generateLegalDocumentSuggestion(String documentType, Map<String, Object> caseInfo) {
        log.info("生成法律文档建议, 文档类型: {}", documentType);
        
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("DOCUMENT_SUGGESTION");
            requestDTO.setModelName("legal-document");
            
            Map<String, Object> params = new HashMap<>(caseInfo);
            params.put("document_type", documentType);
            requestDTO.setParams(params);
            
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("suggestion");
            } else {
                log.error("生成法律文档建议失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("生成法律文档建议时发生错误", e);
            return null;
        }
    }
    
    /**
     * 案件自动生成文书（支持多类型、风格、要素补全、批量生成）
     * @param docType 文书类型（如起诉状、答辩状、调解书等）
     * @param caseElements 案件要素（如当事人、案由、事实、请求等）
     * @param style 文书风格（如正式、简明、专业、通俗等，可选）
     * @param batch 是否批量生成（如多个案件/多份文书）
     * @param batchList 批量要素列表（可选，batch为true时使用）
     * @return 生成的文书内容或文书列表
     */
    public Object generateLegalDocument(String docType, Map<String, Object> caseElements, String style, boolean batch, List<Map<String, Object>> batchList) {
        log.info("案件自动生成文书，类型: {}，风格: {}，批量: {}", docType, style, batch);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("CASE_DOCUMENT_GENERATE");
            requestDTO.setModelName("case-document-generate");
            Map<String, Object> params = new HashMap<>();
            params.put("docType", docType);
            params.put("elements", caseElements);
            if (style != null) params.put("style", style);
            if (batch) params.put("batch", true);
            if (batch && batchList != null) params.put("batchList", batchList);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                if (batch) {
                    Object result = response.getData().get("documents");
                    if (result instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<String> docs = (List<String>) result;
                        return docs;
                    }
                    return result;
                } else {
                    return response.getData().get("document");
                }
            } else {
                log.error("案件文书自动生成失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("案件文书自动生成时发生错误", e);
            return null;
        }
    }
    
    /**
     * 提取文档关键信息（用于案件要素抽取等）
     * @param content 文档内容
     * @param docType 文档类型（可选）
     * @return 关键信息Map
     */
    public Map<String, Object> extractDocumentInfo(String content, String docType) {
        log.info("提取文档关键信息，类型:{}", docType);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("EXTRACT_DOCUMENT_INFO");
            requestDTO.setModelName("doc-info-extract");
            Map<String, Object> params = new HashMap<>();
            params.put("text", content);
            if (docType != null) params.put("docType", docType);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("文档关键信息提取失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("文档关键信息提取时发生错误", e);
            return Map.of();
        }
    }
} 