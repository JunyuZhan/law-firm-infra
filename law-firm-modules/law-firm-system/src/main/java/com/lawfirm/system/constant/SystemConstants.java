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
    
    /**
     * AI配置API路径前缀
     */
    public static final String API_AI_CONFIG_PREFIX = "/api/v1/system/ai-config";
    
    /**
     * 菜单API路径前缀
     */
    public static final String API_MENU_PREFIX = "/api/v1/menu";
    
    /**
     * 消息API路径前缀
     */
    public static final String API_MESSAGE_PREFIX = "/api/v1/system/message";
    
    /**
     * 消息API子路径常量
     */
    public static final class MessageApi {
        /**
         * 发送站内消息
         */
        public static final String SEND_INTERNAL = API_MESSAGE_PREFIX + "/send/internal";
        
        /**
         * 发送邮件通知
         */
        public static final String SEND_EMAIL = API_MESSAGE_PREFIX + "/send/email";
        
        /**
         * 发送短信通知
         */
        public static final String SEND_SMS = API_MESSAGE_PREFIX + "/send/sms";
        
        /**
         * 发送WebSocket通知
         */
        public static final String SEND_WEBSOCKET = API_MESSAGE_PREFIX + "/send/websocket";
        
        /**
         * 发送多渠道通知
         */
        public static final String SEND_MULTI = API_MESSAGE_PREFIX + "/send/multi";
        
        /**
         * 保存业务消息
         */
        public static final String SAVE = API_MESSAGE_PREFIX + "/save";
        
        /**
         * 测试消息服务
         */
        public static final String TEST = API_MESSAGE_PREFIX + "/test";
        
        /**
         * 消息配置管理前缀
         */
        public static final String CONFIG_PREFIX = API_MESSAGE_PREFIX + "/config";
        
        /**
         * 消息服务配置概览
         */
        public static final String CONFIG_OVERVIEW = CONFIG_PREFIX + "/overview";
        
        /**
         * 测试邮件服务
         */
        public static final String CONFIG_TEST_EMAIL = CONFIG_PREFIX + "/test/email";
        
        /**
         * 测试短信服务
         */
        public static final String CONFIG_TEST_SMS = CONFIG_PREFIX + "/test/sms";
        
        /**
         * 邮件配置建议
         */
        public static final String CONFIG_EMAIL_SUGGESTIONS = CONFIG_PREFIX + "/suggestions/email";
        
        /**
         * 短信服务商建议
         */
        public static final String CONFIG_SMS_SUGGESTIONS = CONFIG_PREFIX + "/suggestions/sms";
        
        /**
         * 验证邮件配置
         */
        public static final String CONFIG_VALIDATE_EMAIL = CONFIG_PREFIX + "/validate/email";
    }
} 