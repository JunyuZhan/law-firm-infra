package com.lawfirm.api.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Flyway迁移配置类 - 主要执行API模块的迁移脚本
 * 各业务模块通过Spring Boot自动配置自行处理各自的迁移脚本
 */
@Slf4j
@Configuration
public class FlywayConfig {

    @Autowired
    private Environment environment;

    /**
     * 自定义API模块的Flyway配置
     * 注意：不覆盖其他模块的Flyway配置
     */
    @Bean(name = "apiFlywayInitializer")
    @DependsOn("dataSource")
    public Flyway flyway(DataSource dataSource) {
        log.info("初始化API模块的Flyway配置...");
        
        // 获取是否启用flyway的配置，默认启用
        boolean enabled = environment.getProperty("spring.flyway.enabled", Boolean.class, true);
        if (!enabled) {
            log.info("Flyway迁移已禁用");
            return null;
        }

        try {
            // 只配置API模块的迁移脚本位置
            Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:META-INF/db/migration")
                .baselineOnMigrate(true)
                .baselineVersion("1000")
                .validateOnMigrate(true)
                .outOfOrder(true)
                .load();

            // 执行修复和迁移
            log.info("执行API模块的Flyway修复...");
            flyway.repair();
            
            log.info("执行API模块的Flyway迁移...");
            flyway.migrate();
            log.info("API模块的Flyway迁移完成");
            
            return flyway;
        } catch (Exception e) {
            log.error("API模块的数据库迁移失败: {}", e.getMessage(), e);
            log.warn("迁移失败，但允许应用继续启动");
            return null;
        }
    }
} 