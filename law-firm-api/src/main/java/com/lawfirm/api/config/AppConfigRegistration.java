package com.lawfirm.api.config;

import com.lawfirm.api.config.refresh.ConfigRefreshProperties;
import com.lawfirm.api.config.validation.AppProperties;
import com.lawfirm.api.config.validation.JwtProperties;
import com.lawfirm.api.config.validation.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置注册类
 * 用于启用所有的配置属性类
 */
@Configuration
@EnableConfigurationProperties({
    AppProperties.class,
    JwtProperties.class,
    ThreadPoolProperties.class,
    ConfigRefreshProperties.class
})
public class AppConfigRegistration {
    // 配置注册类，无需额外方法
} 