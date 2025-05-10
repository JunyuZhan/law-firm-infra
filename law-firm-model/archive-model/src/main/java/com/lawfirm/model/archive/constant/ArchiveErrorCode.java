package com.lawfirm.model.archive.constant;

/**
 * 档案模块错误码常量类
 */
public class ArchiveErrorCode {
    
    /**
     * 模块错误码前缀
     */
    public static final String MODULE_PREFIX = "ARCHIVE_";
    
    /**
     * 档案不存在
     */
    public static final String ARCHIVE_NOT_FOUND = MODULE_PREFIX + "NOT_FOUND";
    
    /**
     * 档案已存在
     */
    public static final String ARCHIVE_ALREADY_EXISTS = MODULE_PREFIX + "ALREADY_EXISTS";
    
    /**
     * 档案同步失败
     */
    public static final String SYNC_FAILED = MODULE_PREFIX + "SYNC_FAILED";
    
    /**
     * 同步系统不存在
     */
    public static final String SYNC_SYSTEM_NOT_FOUND = MODULE_PREFIX + "SYNC_SYSTEM_NOT_FOUND";
    
    /**
     * 同步系统未启用
     */
    public static final String SYNC_SYSTEM_DISABLED = MODULE_PREFIX + "SYNC_SYSTEM_DISABLED";
    
    /**
     * 同步连接超时
     */
    public static final String SYNC_CONNECTION_TIMEOUT = MODULE_PREFIX + "SYNC_CONNECTION_TIMEOUT";
    
    /**
     * 档案文件不存在
     */
    public static final String ARCHIVE_FILE_NOT_FOUND = MODULE_PREFIX + "FILE_NOT_FOUND";
    
    /**
     * 档案文件已借出
     */
    public static final String ARCHIVE_FILE_ALREADY_BORROWED = MODULE_PREFIX + "FILE_ALREADY_BORROWED";
    
    /**
     * 档案文件未借出
     */
    public static final String ARCHIVE_FILE_NOT_BORROWED = MODULE_PREFIX + "FILE_NOT_BORROWED";
    
    /**
     * 档案状态错误
     */
    public static final String ARCHIVE_STATUS_ERROR = MODULE_PREFIX + "STATUS_ERROR";
    
    /**
     * 参数错误
     */
    public static final String PARAMETER_ERROR = MODULE_PREFIX + "PARAMETER_ERROR";
    
    /**
     * 操作失败
     */
    public static final String OPERATION_FAILED = MODULE_PREFIX + "OPERATION_FAILED";
} 