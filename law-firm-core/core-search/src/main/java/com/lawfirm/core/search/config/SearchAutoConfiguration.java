package com.lawfirm.core.search.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 搜索模块自动配置类
 */
@Configuration
@ImportAutoConfiguration({
    SearchPropertiesProvider.class,
    DatabaseSearchConfig.class,
    LuceneSearchConfig.class,
    SearchProperties.class
})
public class SearchAutoConfiguration {
    // 自动配置
} 