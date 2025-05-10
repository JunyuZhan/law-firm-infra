package com.lawfirm.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * 基础自动配置类
 * 用于继承实现各模块的自动配置
 * 作为所有自动配置的基类，提供基础导入功能
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "law-firm.common.base", name = "enabled", matchIfMissing = true)
@Import({BaseConfig.class})
public class BaseAutoConfiguration {
    // 基础配置类，提供系统级别的自动配置支持
} 