package com.lawfirm.client.constant;

/**
 * 客户业务模块常量定义
 */
public class ClientModuleConstant {

    /**
     * 缓存相关常量
     */
    public static final class Cache {
        /**
         * 客户缓存前缀
         */
        public static final String CLIENT_CACHE_PREFIX = "law_firm:client:info:";
        
        /**
         * 客户列表缓存前缀
         */
        public static final String CLIENT_LIST_CACHE_PREFIX = "law_firm:client:list:";
        
        /**
         * 客户联系人缓存前缀
         */
        public static final String CLIENT_CONTACTS_CACHE_PREFIX = "law_firm:client:contacts:";
        
        /**
         * 标签缓存前缀
         */
        public static final String TAG_CACHE_PREFIX = "law_firm:client:tag:";
        
        /**
         * 分类缓存前缀
         */
        public static final String CATEGORY_CACHE_PREFIX = "law_firm:client:category:";
        
        /**
         * 客户缓存过期时间（小时）
         */
        public static final int CLIENT_CACHE_EXPIRE_HOURS = 24;
        
        /**
         * 列表缓存过期时间（分钟）
         */
        public static final int LIST_CACHE_EXPIRE_MINUTES = 30;
    }
    
    /**
     * 导入导出相关常量
     */
    public static final class ImportExport {
        /**
         * 导入模板文件名
         */
        public static final String IMPORT_TEMPLATE_FILENAME = "client_import_template.xlsx";
        
        /**
         * 导出默认文件名
         */
        public static final String EXPORT_DEFAULT_FILENAME = "客户数据导出";
        
        /**
         * 单次批量导入最大记录数
         */
        public static final int MAX_IMPORT_BATCH_SIZE = 1000;
        
        /**
         * 导入数据临时目录
         */
        public static final String IMPORT_TEMP_DIR = "temp/import/client/";
    }
    
    /**
     * 任务相关常量
     */
    public static final class Task {
        /**
         * 跟进提醒定时任务CRON表达式（每天早上8点执行）
         */
        public static final String FOLLOW_UP_REMINDER_CRON = "0 0 8 * * ?";
        
        /**
         * 客户状态检查定时任务CRON表达式（每天凌晨2点执行）
         */
        public static final String CLIENT_STATUS_CHECK_CRON = "0 0 2 * * ?";
        
        /**
         * 最大重试次数
         */
        public static final int MAX_RETRY_COUNT = 3;
    }
    
    /**
     * 业务流程相关常量
     */
    public static final class Process {
        /**
         * 客户默认跟进周期（天）
         */
        public static final int DEFAULT_FOLLOW_CYCLE_DAYS = 30;
        
        /**
         * VIP客户默认跟进周期（天）
         */
        public static final int VIP_FOLLOW_CYCLE_DAYS = 15;
        
        /**
         * 核心客户默认跟进周期（天）
         */
        public static final int CORE_FOLLOW_CYCLE_DAYS = 7;
        
        /**
         * 客户沉默期阈值（天）
         */
        public static final int CLIENT_SILENCE_THRESHOLD_DAYS = 90;
        
        /**
         * 客户流失阈值（天）
         */
        public static final int CLIENT_LOSS_THRESHOLD_DAYS = 180;
    }
    
    /**
     * 消息相关常量
     */
    public static final class Message {
        /**
         * 客户创建消息模板
         */
        public static final String CLIENT_CREATED_TEMPLATE = "client.created";
        
        /**
         * 跟进提醒消息模板
         */
        public static final String FOLLOW_UP_REMINDER_TEMPLATE = "client.followup.reminder";
        
        /**
         * 客户状态变更消息模板
         */
        public static final String CLIENT_STATUS_CHANGED_TEMPLATE = "client.status.changed";
    }
    
    /**
     * 权限相关常量
     */
    public static final class Permission {
        /**
         * 客户管理基础权限
         */
        public static final String CLIENT_BASE = "client";
        
        /**
         * 客户查看权限
         */
        public static final String CLIENT_VIEW = "client:view";
        
        /**
         * 客户创建权限
         */
        public static final String CLIENT_CREATE = "client:create";
        
        /**
         * 客户编辑权限
         */
        public static final String CLIENT_EDIT = "client:edit";
        
        /**
         * 客户删除权限
         */
        public static final String CLIENT_DELETE = "client:delete";
        
        /**
         * 客户导入导出权限
         */
        public static final String CLIENT_IMPORT_EXPORT = "client:import-export";
        
        /**
         * 跟进记录管理权限
         */
        public static final String FOLLOW_UP = "client:follow-up";
    }
} 