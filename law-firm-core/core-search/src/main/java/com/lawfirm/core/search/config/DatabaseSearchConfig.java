package com.lawfirm.core.search.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库搜索配置
 * 当配置文件中指定database作为搜索类型时激活
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "database", matchIfMissing = true)
public class DatabaseSearchConfig {
    
    public DatabaseSearchConfig() {
        log.info("初始化数据库搜索配置");
    }
} 