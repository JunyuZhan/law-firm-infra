package com.lawfirm.model.archive.constant;

/**
 * 档案管理常量类
 */
public class ArchiveConstant {
    
    /**
     * 模块名称
     */
    public static final String MODULE_NAME = "archive";
    
    /**
     * 档案状态：已归档
     */
    public static final int ARCHIVE_STATUS_ARCHIVED = 1;
    
    /**
     * 档案状态：已同步
     */
    public static final int ARCHIVE_STATUS_SYNCED = 2;
    
    /**
     * 档案状态：已销毁
     */
    public static final int ARCHIVE_STATUS_DESTROYED = 3;
    
    /**
     * 同步状态：未同步
     */
    public static final int SYNC_STATUS_UNSYNC = 0;
    
    /**
     * 同步状态：已同步
     */
    public static final int SYNC_STATUS_SYNCED = 1;
    
    /**
     * 借阅状态：未借出
     */
    public static final int BORROW_STATUS_NORMAL = 0;
    
    /**
     * 借阅状态：已借出
     */
    public static final int BORROW_STATUS_BORROWED = 1;
    
    /**
     * 默认最大重试次数
     */
    public static final int DEFAULT_MAX_RETRY = 3;
    
    /**
     * 默认超时时间（秒）
     */
    public static final int DEFAULT_TIMEOUT = 60;
    
    /**
     * 默认批量同步大小
     */
    public static final int DEFAULT_BATCH_SIZE = 100;
    
    /**
     * 启用状态
     */
    public static final int ENABLED = 1;
    
    /**
     * 禁用状态
     */
    public static final int DISABLED = 0;
    
    /**
     * 同步方式：手动
     */
    public static final int SYNC_MODE_MANUAL = 1;
    
    /**
     * 同步方式：自动
     */
    public static final int SYNC_MODE_AUTO = 2;
} 