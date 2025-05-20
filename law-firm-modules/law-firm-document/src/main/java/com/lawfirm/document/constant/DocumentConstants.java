package com.lawfirm.document.constant;

/**
 * 文档模块常量定义
 */
public final class DocumentConstants {
    
    private DocumentConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/documents";
    
    /**
     * 文档分类API路径前缀
     */
    public static final String API_CATEGORY_PREFIX = "/api/v1/document-categories";
    
    /**
     * 文档模板API路径前缀
     */
    public static final String API_TEMPLATE_PREFIX = "/api/v1/document-templates";
    
    /**
     * 文档权限API路径前缀
     */
    public static final String API_PERMISSION_PREFIX = "/api/v1/document-permissions";
    
    /**
     * 文件操作API路径前缀
     */
    public static final String API_FILE_PREFIX = "/api/v1/files";
    
    /**
     * 文档预览API路径前缀
     */
    public static final String API_PREVIEW_PREFIX = "/api/v1/document-previews";
    
    /**
     * 文档标签API路径前缀
     */
    public static final String API_TAG_PREFIX = "/api/v1/document-tags";
} 