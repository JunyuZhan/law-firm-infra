package com.lawfirm.system.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 系统模块自动配置类
 */
@AutoConfiguration
@ComponentScan("com.lawfirm.system")
@Import({MonitorCacheConfig.class})
public class SystemAutoConfiguration {
    // 系统模块自动配置类
} 