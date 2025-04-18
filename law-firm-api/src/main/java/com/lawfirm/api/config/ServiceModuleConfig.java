package com.lawfirm.api.config;

import com.lawfirm.core.message.config.MessageAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 服务模块总配置类
 * 用于统一管理各服务模块的配置和启用状态
 */
@Configuration
@PropertySources({
    // 载入默认模块配置
    @PropertySource(value = "classpath:module-config.properties", ignoreResourceNotFound = true)
})
@Import({
    // 导入消息服务自动配置
    MessageAutoConfiguration.class
})
public class ServiceModuleConfig {
    // 此类主要用于组织和导入各模块的配置
    // 具体模块配置由各自的@Configuration类实现
} 