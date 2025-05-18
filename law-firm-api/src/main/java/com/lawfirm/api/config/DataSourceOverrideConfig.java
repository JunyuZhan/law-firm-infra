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
            // 优先使用spring.datasource标准配置
            String jdbcUrl = environment.getProperty("spring.datasource.url");
            String username = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
            
            // 如果标准配置中没有值，再尝试从环境变量获取
            if (username == null) {
                username = environment.getProperty("MYSQL_USERNAME", "root");
            }
            
            if (password == null) {
                password = environment.getProperty("MYSQL_PASSWORD", "");
            }
            
            if (driverClassName == null) {
                driverClassName = "com.mysql.cj.jdbc.Driver";
            }
            
            // 设置数据库驱动 - 必须在jdbcUrl之前设置
            dataSource.setDriverClassName(driverClassName);
            
            // 设置连接信息
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            
            // 如果jdbcUrl未配置，则从其他配置构建
            if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
                String host = environment.getProperty("MYSQL_HOST", "localhost");
                String port = environment.getProperty("MYSQL_PORT", "3306");
                String database = environment.getProperty("spring.database.name");
                if (database == null) {
                    // 尝试从数据源配置获取
                    database = environment.getProperty("spring.datasource.database-name");
                    // 如果还是为空，再使用环境变量或默认值
                    if (database == null) {
                        database = environment.getProperty("MYSQL_DATABASE", "law_firm");
                    }
                }
                
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