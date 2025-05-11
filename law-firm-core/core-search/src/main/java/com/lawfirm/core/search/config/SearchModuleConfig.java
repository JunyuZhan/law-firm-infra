package com.lawfirm.core.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索模块MyBatis配置
 * 扫描搜索相关的Mapper接口
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.core.search", name = "enabled", havingValue = "true", matchIfMissing = true)
@MapperScan(basePackages = {"com.lawfirm.model.search.mapper"})
public class SearchModuleConfig {
    
    public SearchModuleConfig() {
        log.info("初始化搜索模块MyBatis配置，扫描Mapper: com.lawfirm.model.search.mapper");
    }
} 