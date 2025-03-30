package com.lawfirm.core.audit.config;

import com.lawfirm.common.log.properties.LogProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * 开发环境日志配置类
 * <p>
 * 为开发环境提供简化的日志配置
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev", matchIfMissing = true)
public class DevLogConfig {

    /**
     * 提供开发环境日志属性Bean
     * <p>
     * 用于简化开发环境的日志配置，禁用复杂日志功能
     * </p>
     * 
     * @return 日志属性配置
     */
    @Bean
    @Primary
    public LogProperties devLogProperties() {
        log.info("初始化开发环境日志配置");
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