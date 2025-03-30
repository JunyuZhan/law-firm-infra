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
    // 不再导入接口类型，只导入具体配置类
    // SearchPropertiesProvider.class,
    DatabaseSearchConfig.class,
    LuceneSearchConfig.class,
    SearchProperties.class,
    SearchEngineTypeConfig.class,
    SearchPropertiesProviderImpl.class
})
public class SearchAutoConfiguration {
    
    /**
     * 当没有SearchPropertiesProvider实现类时提供默认实现
     * 由SearchPropertiesProviderImpl提供，不再需要
     */
    /*
    @Bean
    @ConditionalOnMissingBean(SearchPropertiesProvider.class)
    public SearchPropertiesProvider defaultSearchPropertiesProvider(SearchProperties searchProperties) {
        return () -> searchProperties;
    }
    */
} 