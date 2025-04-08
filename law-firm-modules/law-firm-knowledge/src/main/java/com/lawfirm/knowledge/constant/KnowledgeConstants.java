package com.lawfirm.knowledge.constant;

/**
 * 知识管理模块常量定义
 */
public final class KnowledgeConstants {
    
    private KnowledgeConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/knowledge";
    
    /**
     * 知识分类API路径前缀
     */
    public static final String API_CATEGORY_PREFIX = "/api/v1/knowledge-categories";
    
    /**
     * 知识标签API路径前缀
     */
    public static final String API_TAG_PREFIX = "/api/v1/knowledge-tags";
    
    /**
     * 知识附件API路径前缀
     */
    public static final String API_ATTACHMENT_PREFIX = "/api/v1/knowledge-attachments";
} 