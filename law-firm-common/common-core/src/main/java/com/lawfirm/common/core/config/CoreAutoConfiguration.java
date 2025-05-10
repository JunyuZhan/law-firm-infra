package com.lawfirm.common.core.config;

import com.lawfirm.common.core.config.properties.AppProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 核心模块自动配置类
 * 提供核心功能的自动装配，整合了CommonAutoConfiguration的功能
 * 作为common-core模块的统一配置入口
 */
@AutoConfiguration("commonCoreAutoConfiguration")
@ComponentScan("com.lawfirm.common.core")
@EnableConfigurationProperties(AppProperties.class)
@ConditionalOnProperty(prefix = "law-firm.common.core", name = "enabled", matchIfMissing = true)
@Import({
    BeanConfig.class,
    BaseConfig.class,
    AsyncConfig.class
})
public class CoreAutoConfiguration {
    // 核心模块自动配置类，通过Import引入所有核心配置类
    // 整合了原CommonAutoConfiguration的功能，避免重复配置
} 