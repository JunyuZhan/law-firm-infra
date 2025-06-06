package com.lawfirm.core.audit.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志模块MyBatis配置
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogModuleConfig {
    public LogModuleConfig() {
        log.info("初始化日志模块配置（Mapper扫描由主应用统一处理）");
    }
} 