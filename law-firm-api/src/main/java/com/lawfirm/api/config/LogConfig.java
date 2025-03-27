package com.lawfirm.api.config;

import com.lawfirm.common.log.properties.LogProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 日志配置类
 * 为开发环境提供日志配置
 */
@Configuration
public class LogConfig {

    /**
     * 提供日志属性Bean
     * 用于配置文档模块的日志行为
     */
    @Bean
    @Primary
    public LogProperties logProperties() {
        LogProperties props = new LogProperties();
        // 开发环境禁用复杂日志功能
        props.setEnableMethodLog(false);
        props.setEnableRequestLog(false);
        props.setEnableAsyncLog(false);
        props.setEnableTracing(false);
        props.setLogRequestParams(true);
        props.setLogResponseBody(true);
        props.setLogStackTrace(true);
        props.setExcludePaths(new String[]{"/swagger-ui/**", "/v3/api-docs/**"});
        return props;
    }
} 