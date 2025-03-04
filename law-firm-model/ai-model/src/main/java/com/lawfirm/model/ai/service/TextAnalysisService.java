package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * 文本分析服务接口
 * 提供文本分析、关键信息提取、文本分类等功能
 */
public interface TextAnalysisService {
    
    /**
     * 提取文本中的关键信息
     * 
     * @param text 待分析文本
     * @return 关键信息Map
     */
    Map<String, Object> extractKeyInfo(String text);
    
    /**
     * 生成文本摘要
     * 
     * @param text 原文本
     * @param maxLength 最大摘要长度
     * @return 生成的摘要
     */
    String generateSummary(String text, int maxLength);
    
    /**
     * 计算两段文本的相似度
     * 
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度(0-1)
     */
    double calculateSimilarity(String text1, String text2);
    
    /**
     * 识别文本中的专业术语
     * 
     * @param text 待分析文本
     * @return 术语列表及解释
     */
    Map<String, String> identifyTerms(String text);
    
    /**
     * 对文本进行分类
     * 
     * @param text 待分类文本
     * @return 分类结果及置信度
     */
    Map<String, Double> classifyText(String text);
    
    /**
     * 提取文本中的命名实体
     * 
     * @param text 待分析文本
     * @return 命名实体列表(类型-值)
     */
    Map<String, List<String>> extractEntities(String text);
}
