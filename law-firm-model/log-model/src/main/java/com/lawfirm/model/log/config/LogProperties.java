package com.lawfirm.model.log.config;

/**
 * 日志配置属性接口
 * 定义日志配置的属性访问方法
 */
public interface LogProperties {

    /**
     * 获取是否启用操作日志
     */
    boolean isEnableOperationLog();

    /**
     * 获取是否启用审计日志
     */
    boolean isEnableAuditLog();

    /**
     * 获取是否启用系统日志
     */
    boolean isEnableSystemLog();

    /**
     * 获取是否启用错误日志
     */
    boolean isEnableErrorLog();

    /**
     * 获取日志保留天数
     */
    int getLogRetentionDays();

    /**
     * 获取异步处理线程池大小
     */
    int getAsyncPoolSize();

    /**
     * 获取异步处理队列容量
     */
    int getAsyncQueueCapacity();

    /**
     * 获取导出文件存储路径
     */
    String getExportPath();

    /**
     * 获取导出文件最大大小(MB)
     */
    int getMaxExportFileSize();

    /**
     * 获取单次导出最大记录数
     */
    int getMaxExportRecords();
} 