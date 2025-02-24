package com.lawfirm.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

/**
 * Web模块自动配置
 */
@AutoConfiguration
@ConditionalOnWebApplication
@Import({
    WebConfig.class,
    UploadConfig.class,
    SecurityConfig.class
})
public class WebAutoConfiguration {
} 