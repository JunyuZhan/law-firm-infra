package com.lawfirm.core.audit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志模块MyBatis配置
 * 扫描日志相关的Mapper接口
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = false)
@MapperScan(basePackages = {"com.lawfirm.model.log.mapper"})
public class LogModuleConfig {
    public LogModuleConfig() {
        log.info("初始化日志模块MyBatis配置，扫描Mapper: com.lawfirm.model.log.mapper");
    }
} 