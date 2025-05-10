package com.lawfirm.common.data.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 数据访问自动配置
 * 
 * 职责：
 * 1. 聚合数据访问层的基础配置
 * 2. 不包含缓存相关配置，缓存由common-cache模块提供
 * 3. 提供数据库创建的扩展点，但不包含具体业务模块的数据库
 */
@Configuration("commonDataAutoConfiguration")
@ConditionalOnProperty(prefix = "law-firm.common.data", name = "enabled", matchIfMissing = true)
@Import({
    DataSourceConfig.class,
    MybatisPlusConfig.class,
    SessionFactoryConfig.class,
    DynamicDataSourceConfig.class,
    StorageConfig.class,
    SqlInitDisableConfig.class,
    DatabaseInitializer.class,
    DatabaseRegistry.class
})
public class DataAutoConfiguration {
    // 此类作为配置聚合器，不需要其他实现
}