package com.lawfirm.common.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置属性
 */
@Data
@ConfigurationProperties(prefix = "law-firm.common.log")
public class LogProperties {

    /**
     * 是否启用方法调用日志
     */
    private boolean enableMethodLog = false;

    /**
     * 是否启用请求响应日志
     */
    private boolean enableRequestLog = false;

    /**
     * 是否启用异步日志
     */
    private boolean enableAsyncLog = false;

    /**
     * 是否启用链路追踪
     */
    private boolean enableTracing = false;

    /**
     * 是否记录请求参数
     */
    private boolean logRequestParams = true;

    /**
     * 是否记录响应结果
     */
    private boolean logResponseBody = true;

    /**
     * 是否记录异常堆栈
     */
    private boolean logStackTrace = true;

    /**
     * 需要排除的路径
     */
    private String[] excludePaths = {};

    /**
     * 需要排除的请求参数字段（敏感信息）
     */
    private String[] excludeParamFields = {"password", "token", "authorization"};
} 