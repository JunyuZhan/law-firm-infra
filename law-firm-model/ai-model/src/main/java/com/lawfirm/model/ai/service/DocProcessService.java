package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * 文档处理服务接口
 * 提供文档解析、分类、关键信息提取等功能
 */
public interface DocProcessService {
    
    /**
     * 文档解析与分类
     * 
     * @param docContent 文档内容
     * @return 文档类型及置信度
     */
    Map<String, Double> classifyDocument(String docContent);
    
    /**
     * 从文档中提取关键信息
     * 
     * @param docContent 文档内容
     * @param docType 文档类型（可选）
     * @return 提取的关键信息
     */
    Map<String, Object> extractDocumentInfo(String docContent, String docType);
    
    /**
     * 生成文档摘要
     * 
     * @param docContent 文档内容
     * @param maxLength 最大摘要长度
     * @return 生成的摘要
     */
    String generateDocSummary(String docContent, int maxLength);
    
    /**
     * 分析合同条款
     * 
     * @param contractContent 合同内容
     * @return 条款分析结果
     */
    List<Map<String, Object>> analyzeContractClauses(String contractContent);
    
    /**
     * 智能辅助生成文书
     * 
     * @param templateId 模板ID
     * @param params 参数Map
     * @return 生成的文书内容
     */
    String generateDocument(String templateId, Map<String, Object> params);
    
    /**
     * 对比两份文档的差异
     * 
     * @param doc1 文档1
     * @param doc2 文档2
     * @return 差异点列表
     */
    List<Map<String, Object>> compareDocuments(String doc1, String doc2);
} 