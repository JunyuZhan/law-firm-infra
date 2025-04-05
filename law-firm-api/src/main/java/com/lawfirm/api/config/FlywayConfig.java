package com.lawfirm.api.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Flyway数据库迁移配置
 */
@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", havingValue = "true")
public class FlywayConfig {

    /**
     * 创建Flyway主Bean
     * 
     * @param dataSource 数据源
     * @param flywayProperties Flyway配置属性
     * @return Flyway实例
     */
    @Primary
    @Bean(name = "flyway")
    public Flyway flyway(DataSource dataSource, FlywayProperties flywayProperties) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .validateOnMigrate(flywayProperties.isValidateOnMigrate())
                .table(flywayProperties.getTable())
                .encoding(flywayProperties.getEncoding())
                .placeholderReplacement(true)
                .sqlMigrationSeparator("__")
                .outOfOrder(true)
                .ignoreMigrationPatterns("*:pending")
                .load();
    }

    /**
     * 创建Flyway迁移初始化器
     * 
     * @param flyway Flyway实例
     * @return 迁移初始化器
     */
    @Bean
    @Primary
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }
} 