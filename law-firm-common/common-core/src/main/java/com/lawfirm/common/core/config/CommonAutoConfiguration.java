package com.lawfirm.common.core.config;

import com.lawfirm.common.core.config.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 通用自动配置
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
@Import({
    AsyncConfig.class
})
public class CommonAutoConfiguration {
} 