package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Flyway配置类
 * 用于开发环境下修复迁移脚本校验和问题
 */
@Slf4j
@Configuration("apiFlywayConfig")
public class FlywayConfig {

    /**
     * 自定义Flyway迁移策略，先执行repair再执行migrate
     */
    @Bean("apiFlywayMigrationStrategy")
    @Primary
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // 先执行repair操作修复校验和
            log.info("执行Flyway修复...");
            flyway.repair();
            
            // 然后执行正常迁移
            log.info("执行Flyway迁移...");
            flyway.migrate();
        };
    }
} 