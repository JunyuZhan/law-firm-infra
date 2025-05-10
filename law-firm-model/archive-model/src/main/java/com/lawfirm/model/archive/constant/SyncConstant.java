package com.lawfirm.model.archive.constant;

/**
 * 同步相关常量类
 */
public class SyncConstant {
    
    /**
     * 最大重试次数
     */
    public static final int MAX_RETRY_COUNT = 3;
    
    /**
     * 同步状态：待同步
     */
    public static final int STATUS_PENDING = 0;
    
    /**
     * 同步状态：同步中
     */
    public static final int STATUS_SYNCING = 1;
    
    /**
     * 同步状态：同步成功
     */
    public static final int STATUS_SUCCESS = 2;
    
    /**
     * 同步状态：同步失败
     */
    public static final int STATUS_FAILED = 3;
    
    /**
     * 同步状态：忽略同步
     */
    public static final int STATUS_IGNORED = 9;
    
    /**
     * 同步方式：手动同步
     */
    public static final int MODE_MANUAL = 1;
    
    /**
     * 同步方式：自动同步
     */
    public static final int MODE_AUTO = 2;
    
    /**
     * 增量同步
     */
    public static final int SYNC_TYPE_INCREMENT = 1;
    
    /**
     * 全量同步
     */
    public static final int SYNC_TYPE_FULL = 2;
    
    /**
     * 默认同步频率（分钟）
     */
    public static final int DEFAULT_SYNC_FREQUENCY = 30;
    
    /**
     * 默认超时时间（秒）
     */
    public static final int DEFAULT_TIMEOUT = 60;
    
    /**
     * 默认批量大小
     */
    public static final int DEFAULT_BATCH_SIZE = 100;
    
    /**
     * 同步数据格式：JSON
     */
    public static final String DATA_FORMAT_JSON = "JSON";
    
    /**
     * 同步数据格式：XML
     */
    public static final String DATA_FORMAT_XML = "XML";
} 