package com.lawfirm.common.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 数据访问自动配置
 */
@Configuration
@Import({
    DruidConfig.class,
    MybatisPlusConfig.class,
    JacksonConfig.class,
    RedisConfig.class,
    DynamicDataSourceConfig.class,
    ElasticsearchConfig.class,
    StorageConfig.class
})
public class DataAutoConfiguration {
} 