package com.lawfirm.contract.constant;

/**
 * 合同模块常量定义
 */
public final class ContractConstants {
    
    private ContractConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/contracts";
    
    /**
     * 合同模板API路径前缀
     */
    public static final String API_TEMPLATE_PREFIX = "/api/v1/contract-templates";
    
    /**
     * 合同审核API路径前缀
     */
    public static final String API_REVIEW_PREFIX = "/api/v1/contract-reviews";

    /**
     * 合同冲突API路径前缀
     */
    public static final String API_CONFLICT_PREFIX = "/api/v1/contract-conflicts";
} 