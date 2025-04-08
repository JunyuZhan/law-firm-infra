package com.lawfirm.system.constant;

/**
 * 系统模块常量定义
 */
public final class SystemConstants {
    
    private SystemConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/system";
    
    /**
     * 字典API路径前缀
     */
    public static final String API_DICT_PREFIX = "/api/v1/dictionaries";
    
    /**
     * 字典项API路径前缀
     */
    public static final String API_DICT_ITEM_PREFIX = "/api/v1/dictionary-items";
    
    /**
     * 配置API路径前缀
     */
    public static final String API_CONFIG_PREFIX = "/api/v1/configs";
    
    /**
     * 监控API路径前缀
     */
    public static final String API_MONITOR_PREFIX = "/api/v1/monitors";
    
    /**
     * 升级API路径前缀
     */
    public static final String API_UPGRADE_PREFIX = "/api/v1/upgrades";
} 