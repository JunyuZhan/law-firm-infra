package com.lawfirm.document.manager.ai;

import com.lawfirm.document.model.dto.DocumentDTO;
import java.util.List;

/**
 * 文档AI能力管理器
 */
public interface DocumentAiManager {
    
    /**
     * 智能文档分类
     */
    String classifyDocument(DocumentDTO document);
    
    /**
     * 生成文档摘要
     */
    String generateSummary(DocumentDTO document);
    
    /**
     * 提取关键信息
     */
    List<String> extractKeyInfo(DocumentDTO document);
    
    /**
     * 推荐相似文档
     */
    List<DocumentDTO> recommendSimilarDocuments(DocumentDTO document);
    
    /**
     * 智能标签推荐
     */
    List<String> recommendTags(DocumentDTO document);
    
    /**
     * 敏感信息识别
     */
    List<String> detectSensitiveInfo(DocumentDTO document);
} 