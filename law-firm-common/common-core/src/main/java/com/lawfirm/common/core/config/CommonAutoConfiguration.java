package com.lawfirm.common.core.config;

import com.lawfirm.common.core.config.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 通用自动配置
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class CommonAutoConfiguration {
} 