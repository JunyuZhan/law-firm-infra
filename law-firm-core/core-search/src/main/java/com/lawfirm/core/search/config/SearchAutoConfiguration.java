package com.lawfirm.core.search.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * 搜索模块自动配置类
 */
@Configuration
@ImportAutoConfiguration({
    // 移除对接口的直接引用，接口不能直接实例化
    // SearchPropertiesProvider.class,
    DatabaseSearchConfig.class,
    LuceneSearchConfig.class,
    SearchProperties.class
})
public class SearchAutoConfiguration {
    // 自动配置
    
    /**
     * 当没有SearchPropertiesProvider实现类时提供默认实现
     */
    @Bean
    @ConditionalOnMissingBean(SearchPropertiesProvider.class)
    public SearchPropertiesProvider defaultSearchPropertiesProvider(SearchProperties searchProperties) {
        return () -> searchProperties;
    }
} 