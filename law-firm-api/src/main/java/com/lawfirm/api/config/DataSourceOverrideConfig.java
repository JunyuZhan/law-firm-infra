package com.lawfirm.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源覆盖配置
 * 用于修复MySQL 8连接问题和解决jdbcUrl错误
 */
@Slf4j
@Configuration
@Profile("!apidoc") // 非API文档模式下生效
@Order(10) // 高优先级确保在其他数据源配置之前加载
@ConditionalOnProperty(name = "spring.datasource.enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceOverrideConfig {

    @Autowired
    private Environment environment;

    /**
     * 主数据源配置 - 解决jdbcUrl is required with driverClassName错误
     * 确保MySQL连接参数正确，特别是allowPublicKeyRetrieval=true
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        log.info("创建修复后的数据源配置 - 解决jdbcUrl错误并添加allowPublicKeyRetrieval=true");
        HikariDataSource dataSource = new HikariDataSource();
        
        try {
            // 从环境变量优先获取数据库参数
            String host = environment.getProperty("MYSQL_HOST",
                    environment.getProperty("spring.datasource.host", "localhost"));
            String port = environment.getProperty("MYSQL_PORT",
                    environment.getProperty("spring.datasource.port", "3306"));
            String database = environment.getProperty("MYSQL_DATABASE",
                    environment.getProperty("spring.datasource.database", "law_firm"));
            String username = environment.getProperty("MYSQL_USERNAME",
                    environment.getProperty("spring.datasource.username", "root"));
            String password = environment.getProperty("MYSQL_PASSWORD",
                    environment.getProperty("spring.datasource.password", ""));
                    
            // 设置数据库驱动 - 必须在jdbcUrl之前设置
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            
            // 设置连接信息
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            
            // 优先使用spring.datasource.url配置
            String jdbcUrl = environment.getProperty("spring.datasource.url");
            if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
                // 如果未配置，构建jdbcUrl
                jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai", 
                        host, port, database);
                log.info("未指定JDBC URL，使用构建的URL: {}", jdbcUrl);
            }
            
            // 确保添加allowPublicKeyRetrieval=true参数
            if (!jdbcUrl.contains("allowPublicKeyRetrieval")) {
                jdbcUrl = jdbcUrl + (jdbcUrl.contains("?") ? "&" : "?") + "allowPublicKeyRetrieval=true";
            }
            
            // 确保添加rewriteBatchedStatements=true参数（用于批量插入性能优化）
            if (!jdbcUrl.contains("rewriteBatchedStatements")) {
                jdbcUrl = jdbcUrl + (jdbcUrl.contains("?") ? "&" : "?") + "rewriteBatchedStatements=true";
            }
            
            // 必须先设置jdbcUrl，避免"jdbcUrl is required with driverClassName"错误
            dataSource.setJdbcUrl(jdbcUrl);
            log.info("最终数据源URL: {}", jdbcUrl);
            
            // 连接池配置
            dataSource.setMinimumIdle(environment.getProperty("spring.datasource.hikari.minimum-idle", Integer.class, 5));
            dataSource.setMaximumPoolSize(environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class, 20));
            dataSource.setConnectionTimeout(environment.getProperty("spring.datasource.hikari.connection-timeout", Long.class, 30000L));
            dataSource.setIdleTimeout(environment.getProperty("spring.datasource.hikari.idle-timeout", Long.class, 600000L));
            dataSource.setMaxLifetime(environment.getProperty("spring.datasource.hikari.max-lifetime", Long.class, 1800000L));
            dataSource.setConnectionTestQuery("SELECT 1");
            dataSource.setConnectionInitSql("SET NAMES utf8mb4");
            dataSource.setAutoCommit(true);
        } catch (Exception e) {
            log.error("创建数据源时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("数据源配置错误: " + e.getMessage(), e);
        }
        
        return dataSource;
    }
} 