package com.lawfirm.core.search.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 搜索模块自动配置类
 * 负责加载搜索模块的所有配置
 */
@AutoConfiguration
@Import({
    ElasticsearchConfig.class,
    CoreElasticsearchDisableConfig.class
})
@EnableConfigurationProperties({
    ElasticsearchProperties.class,
    SearchProperties.class
})
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SearchAutoConfiguration {

    /**
     * 创建搜索服务配置Bean
     */
    @Bean
    public SearchProperties searchProperties() {
        return new SearchProperties();
    }
} 