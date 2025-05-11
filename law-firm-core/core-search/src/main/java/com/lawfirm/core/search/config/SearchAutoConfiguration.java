package com.lawfirm.core.search.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 搜索模块自动配置类
 */
@Configuration
@EnableConfigurationProperties(SearchProperties.class)
@Import({
    // 不再导入接口类型，只导入具体配置类
    // SearchPropertiesProvider.class,
    DatabaseSearchConfig.class,
    LuceneSearchConfig.class,
    SearchEngineTypeConfig.class,
    SearchPropertiesProviderImpl.class,
    IndexHandlerConfiguration.class
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