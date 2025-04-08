package com.lawfirm.client.constant;

/**
 * 客户模块常量定义
 */
public final class ClientConstants {
    
    private ClientConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/clients";
    
    /**
     * 客户联系人API路径前缀
     */
    public static final String API_CONTACT_PREFIX = "/api/v1/contacts";
    
    /**
     * 客户分类API路径前缀
     */
    public static final String API_CATEGORY_PREFIX = "/api/v1/client-categories";
    
    /**
     * 客户标签API路径前缀
     */
    public static final String API_TAG_PREFIX = "/api/v1/client-tags";
    
    /**
     * 客户跟进记录API路径前缀
     */
    public static final String API_FOLLOW_UP_PREFIX = "/api/v1/client-follow-ups";
    
    /**
     * 客户导入导出API路径前缀
     */
    public static final String API_IMPORT_EXPORT_PREFIX = "/api/v1/client-import-export";
} 