package com.lawfirm.api.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.util.Arrays;

/**
 * Flyway模块化配置
 * <p>
 * 解决多模块版本冲突问题，为每个模块创建独立的Flyway实例
 * 通过指定不同的命名空间，解决版本号冲突问题
 * </p>
 *
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayModuleConfig {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;
    
    @Value("${flyway.fail-on-missing-locations:false}")
    private boolean failOnMissingLocations;

    /**
     * 系统模块迁移
     */
    @Bean(name = "systemModuleFlyway")
    public Flyway systemModuleFlyway(DataSource dataSource, Environment env) {
        log.info("初始化系统模块Flyway迁移");
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/system")
            .table("flyway_system_history") // 使用不同的历史表
            .baselineOnMigrate(true)
            .outOfOrder(false)
            .validateOnMigrate(true)
            .cleanDisabled(true)
            .sqlMigrationPrefix("V") // 使用默认的V前缀
            .sqlMigrationSeparator(".") // 使用默认的.分隔符
            .schemas("public") // 公共schema
            .load();
    }

    /**
     * 认证模块迁移
     */
    @Bean(name = "authModuleFlyway")
    @DependsOn("systemModuleFlyway")
    public Flyway authModuleFlyway(DataSource dataSource, Environment env) {
        log.info("初始化认证模块Flyway迁移");
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/auth")
            .table("flyway_auth_history") // 使用不同的历史表
            .baselineOnMigrate(true)
            .outOfOrder(false)
            .validateOnMigrate(true)
            .cleanDisabled(true)
            .sqlMigrationPrefix("V") // 使用默认的V前缀
            .sqlMigrationSeparator(".") // 使用默认的.分隔符
            .schemas("public") // 公共schema
            .load();
    }

    /**
     * 客户模块迁移
     */
    @Bean(name = "clientModuleFlyway")
    @DependsOn("authModuleFlyway")
    public Flyway clientModuleFlyway(DataSource dataSource, Environment env) {
        log.info("初始化客户模块Flyway迁移");
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/client")
            .table("flyway_client_history") // 使用不同的历史表
            .baselineOnMigrate(true)
            .outOfOrder(false)
            .validateOnMigrate(true)
            .cleanDisabled(true)
            .sqlMigrationPrefix("V") // 使用默认的V前缀
            .sqlMigrationSeparator(".") // 使用默认的.分隔符
            .schemas("public") // 公共schema
            .load();
    }

    /**
     * 财务模块迁移
     */
    @Bean(name = "financeModuleFlyway")
    @DependsOn("clientModuleFlyway")
    public Flyway financeModuleFlyway(DataSource dataSource, Environment env) {
        log.info("初始化财务模块Flyway迁移");
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/finance")
            .table("flyway_finance_history") // 使用不同的历史表
            .baselineOnMigrate(true)
            .outOfOrder(false)
            .validateOnMigrate(true)
            .cleanDisabled(true)
            .sqlMigrationPrefix("V") // 使用默认的V前缀
            .sqlMigrationSeparator(".") // 使用默认的.分隔符
            .schemas("public") // 公共schema
            .load();
    }
    
    /**
     * Flyway迁移策略，确保即使出错也能继续启动
     */
    @Bean
    @Primary
    public FlywayMigrationStrategy flywayMigrationStrategy(ConfigurableApplicationContext context) {
        return flyway -> {
            // 不在这里执行迁移，使用我们自己的迁移方法
            log.info("使用自定义迁移策略，跳过默认迁移");
        };
    }
    
    /**
     * 初始化所有Flyway实例
     */
    @Bean
    @DependsOn({"systemModuleFlyway", "authModuleFlyway", "clientModuleFlyway", "financeModuleFlyway"})
    public boolean flywayMigrate(
            Flyway systemModuleFlyway,
            Flyway authModuleFlyway,
            Flyway clientModuleFlyway,
            Flyway financeModuleFlyway) {
        log.info("开始执行Flyway数据库迁移，当前环境: {}", activeProfile);
        
        try {
            // 顺序执行各模块的迁移脚本
            MigrateResult systemResult = systemModuleFlyway.migrate();
            log.info("系统模块迁移完成: {} 个脚本执行成功", systemResult.migrationsExecuted);
            
            MigrateResult authResult = authModuleFlyway.migrate();
            log.info("认证模块迁移完成: {} 个脚本执行成功", authResult.migrationsExecuted);
            
            MigrateResult clientResult = clientModuleFlyway.migrate();
            log.info("客户模块迁移完成: {} 个脚本执行成功", clientResult.migrationsExecuted);
            
            MigrateResult financeResult = financeModuleFlyway.migrate();
            log.info("财务模块迁移完成: {} 个脚本执行成功", financeResult.migrationsExecuted);
            
            log.info("所有模块数据库迁移完成，总计执行: {} 个脚本", 
                    systemResult.migrationsExecuted + authResult.migrationsExecuted + 
                    clientResult.migrationsExecuted + financeResult.migrationsExecuted);
            
            return true;
        } catch (Exception e) {
            log.error("数据库迁移过程中发生错误: {}", e.getMessage(), e);
            // 开发环境可以继续启动，生产环境必须停止
            boolean isDevEnv = "dev".equalsIgnoreCase(activeProfile) || 
                               "local".equalsIgnoreCase(activeProfile);
            if(isDevEnv) {
                log.warn("开发环境，忽略迁移错误，继续启动");
                return false;
            } else {
                log.error("生产环境，迁移失败，应用将停止启动");
                throw new RuntimeException("数据库迁移失败，应用无法启动", e);
            }
        }
    }
} 