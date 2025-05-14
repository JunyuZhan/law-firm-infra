package com.lawfirm.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

/**
 * Web模块自动配置
 * 
 * 提供Web相关配置的统一入口
 */
@AutoConfiguration("commonWebAutoConfiguration")
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "law-firm.common.web", name = "enabled", matchIfMissing = true)
@Import({
    WebConfig.class,
    JacksonConfig.class,
    UploadConfig.class,
    SecurityConfig.class
})
public class WebAutoConfiguration {
    // 此类作为配置聚合器，不需要其他实现
} 