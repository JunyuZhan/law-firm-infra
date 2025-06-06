package com.lawfirm.core.search.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索模块MyBatis配置
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.core.search", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SearchModuleConfig {
    
    public SearchModuleConfig() {
        log.info("初始化搜索模块配置（Mapper扫描由主应用统一处理）");
    }
} 