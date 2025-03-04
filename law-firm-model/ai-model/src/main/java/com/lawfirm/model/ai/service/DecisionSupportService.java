package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * 决策支持服务接口
 * 提供案件分析、风险评估、判例推荐等功能
 */
public interface DecisionSupportService {
    
    /**
     * 案件风险评估
     * 
     * @param caseInfo 案件信息
     * @return 风险评估结果
     */
    Map<String, Object> assessCaseRisk(Map<String, Object> caseInfo);
    
    /**
     * 诉讼结果预测
     * 
     * @param caseInfo 案件信息
     * @return 预测结果及置信度
     */
    Map<String, Object> predictLitigationOutcome(Map<String, Object> caseInfo);
    
    /**
     * 推荐相关判例
     * 
     * @param caseDescription 案件描述
     * @param limit 最大返回数量
     * @return 相关判例列表
     */
    List<Map<String, Object>> recommendPrecedents(String caseDescription, int limit);
    
    /**
     * 法规适用建议
     * 
     * @param caseInfo 案件信息
     * @return 适用法规及理由
     */
    List<Map<String, Object>> suggestApplicableLaws(Map<String, Object> caseInfo);
    
    /**
     * 案例相似度分析
     * 
     * @param caseId 当前案件ID
     * @param limit 最大返回数量
     * @return 相似案例及相似度
     */
    List<Map<String, Object>> findSimilarCases(String caseId, int limit);
    
    /**
     * 辩护策略建议
     * 
     * @param caseInfo 案件信息
     * @return 策略建议列表
     */
    List<String> suggestDefenseStrategies(Map<String, Object> caseInfo);
} 