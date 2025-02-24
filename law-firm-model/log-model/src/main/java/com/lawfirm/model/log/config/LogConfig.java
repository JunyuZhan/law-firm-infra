package com.lawfirm.model.log.config;

import com.lawfirm.common.log.properties.LogProperties;

/**
 * 日志配置接口
 * 定义日志模型的配置方法
 */
public interface LogConfig {

    /**
     * 获取通用日志配置
     */
    LogProperties getLogProperties();

    /**
     * 判断指定类型的日志是否启用
     * @param logType 日志类型
     * @return 是否启用
     */
    boolean isLogEnabled(String logType);

    /**
     * 获取日志存储路径
     */
    String getLogStorePath();

    /**
     * 获取日志导出路径
     */
    String getLogExportPath();
} 