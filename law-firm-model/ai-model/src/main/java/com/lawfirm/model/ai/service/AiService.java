package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * AI服务接口
 * 提供AI相关功能，如文档摘要生成、关键信息提取等
 */
public interface AiService {
    
    /**
     * 生成文本摘要
     * 
     * @param content 原文本内容
     * @return 生成的摘要
     */
    String generateSummary(String content);
    
    /**
     * 提取关键信息
     * 
     * @param content 文本内容
     * @return 关键信息映射
     */
    Map<String, Object> extractKeyInfo(String content);
    
    /**
     * 智能分类
     * 
     * @param content 文本内容
     * @return 分类结果及置信度
     */
    Map<String, Double> classify(String content);
    
    /**
     * 相似度计算
     * 
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度(0-1)
     */
    double calculateSimilarity(String text1, String text2);
    
    /**
     * 关键词提取
     * 
     * @param content 文本内容
     * @param limit 最大返回数量
     * @return 关键词列表
     */
    List<String> extractKeywords(String content, int limit);
    
    /**
     * 命名实体识别
     * 
     * @param content 文本内容
     * @return 实体类型及值
     */
    Map<String, List<String>> extractEntities(String content);
    
    /**
     * 情感分析
     * 
     * @param content 文本内容
     * @return 情感得分(-1到1)
     */
    double analyzeSentiment(String content);
} 