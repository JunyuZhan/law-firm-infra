package com.lawfirm.api.config;

import com.lawfirm.common.data.config.DataSourceEnhancementConfig.DatabasePasswordDecryptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * API层数据源配置
 * 
 * 主要功能：
 * 1. 提供带密码处理功能的数据源
 * 2. 优先于common-data模块的DataSourceConfig加载
 * 3. 支持密码加密功能
 * 
 * 注意：此配置仅在启用API模块特定的数据库密码加密功能时生效
 * 否则应使用common-data模块提供的DataSourceConfig
 */
@Slf4j
@Configuration("apiDataSourceConfig")
@ConditionalOnProperty(name = {"law-firm.security.db-password.enabled", "law-firm.api.datasource.override"}, 
                       havingValue = "true", matchIfMissing = false)
public class ApiDataSourceConfig {

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private DatabaseSecurityConfig.ApiDatabasePasswordProcessor passwordProcessor;
    
    @Autowired(required = false)
    private DatabasePasswordDecryptor databasePasswordDecryptor;

    /**
     * 创建带密码处理的数据源
     * 使用@Primary确保优先使用此数据源
     * 仅在common-data模块未提供dataSource Bean时创建
     */
    @Bean("apiDataSource")
    @Primary
    @ConditionalOnMissingBean(name = {"dataSource", "commonDataSource"})
    public DataSource apiSecureDataSource() {
        log.info("创建API层支持密码处理的数据源");
        
        // 读取数据库配置
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        
        // 处理密码 - 优先使用common-data模块的解密器
        if (password != null) {
            String originalPassword = password;
            if (databasePasswordDecryptor != null) {
                password = databasePasswordDecryptor.decrypt(password);
                if (!password.equals(originalPassword)) {
                    log.info("使用common-data模块的解密器处理数据库密码");
                }
            } else if (passwordProcessor != null) {
                password = passwordProcessor.processPassword(password);
                if (!password.equals(originalPassword)) {
                    log.info("使用API模块的密码处理器处理数据库密码");
                }
            }
        }
        
        // 创建HikariCP配置
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        
        // 设置连接池参数
        config.setMinimumIdle(environment.getProperty("spring.datasource.hikari.minimum-idle", Integer.class, 5));
        config.setMaximumPoolSize(environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class, 20));
        config.setIdleTimeout(environment.getProperty("spring.datasource.hikari.idle-timeout", Long.class, 30000L));
        config.setMaxLifetime(environment.getProperty("spring.datasource.hikari.max-lifetime", Long.class, 1800000L));
        config.setConnectionTimeout(environment.getProperty("spring.datasource.hikari.connection-timeout", Long.class, 30000L));
        config.setPoolName(environment.getProperty("spring.datasource.hikari.pool-name", "ApiHikariCP"));
        
        // 设置连接测试
        config.setConnectionTestQuery("SELECT 1");
        
        // 设置连接初始化SQL
        config.setConnectionInitSql("SET NAMES utf8mb4");
        
        // 设置自动提交
        config.setAutoCommit(true);
        
        return new HikariDataSource(config);
    }
} 