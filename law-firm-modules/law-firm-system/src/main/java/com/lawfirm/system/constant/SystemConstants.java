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
    
    /**
     * 数据库备份API路径前缀
     */
    public static final String API_DATABASE_PREFIX = "/api/v1/database";
    
    /**
     * 日志API路径前缀
     */
    public static final String API_LOG_PREFIX = "/api/v1/monitors/logs";
    
    /**
     * 审计API路径前缀
     */
    public static final String API_AUDIT_PREFIX = "/api/v1/monitors/audits";
    
    /**
     * 告警API路径前缀
     */
    public static final String API_ALERT_PREFIX = "/api/v1/monitors/alerts";
    
    /**
     * 应用监控API路径前缀
     */
    public static final String API_APP_MONITOR_PREFIX = "/api/v1/monitors/apps";
    
    /**
     * 数据库监控API路径前缀
     */
    public static final String API_DB_MONITOR_PREFIX = "/api/v1/monitors/databases";
} 