package com.lawfirm.client.constant;

/**
 * 缓存常量定义
 */
public class CacheConstant {

    /**
     * 缓存键名命名规则：业务模块:业务领域:数据类型:标识
     */
    
    /**
     * 客户模块缓存前缀
     */
    public static final String MODULE_PREFIX = "law_firm:client:";
    
    /**
     * 客户信息缓存
     */
    public static final String CLIENT_INFO = MODULE_PREFIX + "info:";
    
    /**
     * 客户列表缓存
     */
    public static final String CLIENT_LIST = MODULE_PREFIX + "list:";
    
    /**
     * 客户统计数据缓存
     */
    public static final String CLIENT_STATS = MODULE_PREFIX + "stats:";
    
    /**
     * 客户联系人缓存
     */
    public static final String CLIENT_CONTACTS = MODULE_PREFIX + "contacts:";
    
    /**
     * 客户地址缓存
     */
    public static final String CLIENT_ADDRESS = MODULE_PREFIX + "address:";
    
    /**
     * 客户跟进记录缓存
     */
    public static final String CLIENT_FOLLOW_UP = MODULE_PREFIX + "follow_up:";
    
    /**
     * 分类缓存
     */
    public static final String CATEGORY = MODULE_PREFIX + "category:";
    
    /**
     * 标签缓存
     */
    public static final String TAG = MODULE_PREFIX + "tag:";
    
    /**
     * 客户标签关联缓存
     */
    public static final String CLIENT_TAG_RELATION = MODULE_PREFIX + "tag_relation:";
    
    /**
     * 缓存过期时间（秒）
     */
    public static final class ExpireTime {
        /**
         * 客户信息缓存过期时间：24小时
         */
        public static final int CLIENT_INFO = 24 * 60 * 60;
        
        /**
         * 列表缓存过期时间：30分钟
         */
        public static final int LIST = 30 * 60;
        
        /**
         * 统计数据缓存过期时间：1小时
         */
        public static final int STATS = 60 * 60;
        
        /**
         * 分类和标签缓存过期时间：12小时
         */
        public static final int CATEGORY_AND_TAG = 12 * 60 * 60;
    }
    
    /**
     * 锁定键前缀
     */
    public static final class Lock {
        /**
         * 客户创建锁定键前缀
         */
        public static final String CLIENT_CREATE = MODULE_PREFIX + "lock:create:";
        
        /**
         * 客户更新锁定键前缀
         */
        public static final String CLIENT_UPDATE = MODULE_PREFIX + "lock:update:";
        
        /**
         * 导入锁定键前缀
         */
        public static final String IMPORT = MODULE_PREFIX + "lock:import:";
        
        /**
         * 锁定超时时间（毫秒）：30秒
         */
        public static final long TIMEOUT = 30 * 1000;
    }
} 