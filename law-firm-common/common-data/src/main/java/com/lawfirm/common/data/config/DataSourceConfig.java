package com.lawfirm.common.data.config;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据源配置类
 * 提供统一的数据源配置，避免硬编码和配置分散
 * 只有在law-firm.common.data.enabled=true或未设置时才启用
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "law-firm.common.data.enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceConfig {
    
    @Autowired
    private Environment environment;

    /**
     * 创建主数据源
     * 优先使用spring.datasource标准配置，兼容law-firm.common.data配置
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        log.info("数据库服务已启用，创建数据源");
        
        // 从环境变量获取数据库参数
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
        
        // 优先使用标准的spring.datasource.url配置
        String url = environment.getProperty("spring.datasource.url");
        
        // 如果URL不存在，则构建一个
        if (url == null || url.trim().isEmpty()) {
            url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true", 
                    host, port, database);
        }
        
        log.info("创建数据源：{}, username: {}", url, username);
        
        // 提取数据库名称
        String databaseName = extractDatabaseName(url);
        // 尝试创建数据库
        createDatabaseIfNotExists(databaseName, host, port, username, password);
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"));
        
        // 连接池配置 - 优先使用标准配置
        Integer minIdle = environment.getProperty("spring.datasource.hikari.minimum-idle", Integer.class);
        if (minIdle == null) {
            minIdle = environment.getProperty("law-firm.common.data.pool.min-idle", Integer.class, 5);
        }
        config.setMinimumIdle(minIdle);
        
        Integer maxPoolSize = environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class);
        if (maxPoolSize == null) {
            maxPoolSize = environment.getProperty("law-firm.common.data.pool.max-pool-size", Integer.class, 20);
        }
        config.setMaximumPoolSize(maxPoolSize);
        
        Long connTimeout = environment.getProperty("spring.datasource.hikari.connection-timeout", Long.class);
        if (connTimeout == null) {
            connTimeout = environment.getProperty("law-firm.common.data.pool.connection-timeout", Long.class, 30000L);
        }
        config.setConnectionTimeout(connTimeout);
        
        Long idleTimeout = environment.getProperty("spring.datasource.hikari.idle-timeout", Long.class);
        if (idleTimeout == null) {
            idleTimeout = environment.getProperty("law-firm.common.data.pool.idle-timeout", Long.class, 600000L);
        }
        config.setIdleTimeout(idleTimeout);
        
        Long maxLifetime = environment.getProperty("spring.datasource.hikari.max-lifetime", Long.class);
        if (maxLifetime == null) {
            maxLifetime = environment.getProperty("law-firm.common.data.pool.max-lifetime", Long.class, 1800000L);
        }
        config.setMaxLifetime(maxLifetime);
        
        // 连接测试
        config.setConnectionTestQuery("SELECT 1");
        
        // 连接初始化SQL
        config.setConnectionInitSql("SET NAMES utf8mb4");
        
        // 其他配置
        config.setAutoCommit(true);
        
        return new HikariDataSource(config);
    }
    
    /**
     * 从JDBC URL中提取数据库名称
     */
    private String extractDatabaseName(String jdbcUrl) {
        Pattern pattern = Pattern.compile("jdbc:mysql://[^/]+/([^?]+)");
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "law_firm"; // 默认数据库名
    }
    
    /**
     * 尝试创建数据库（如果不存在）
     */
    private void createDatabaseIfNotExists(String databaseName, String host, String port, String username, String password) {
        // 构建不包含数据库名的连接URL
        String baseUrl = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", 
                host, port);
        
        try {
            // 确保驱动已加载
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                log.warn("无法加载MySQL驱动", ex);
            }
            
            // 建立连接并创建数据库
            try (Connection connection = java.sql.DriverManager.getConnection(baseUrl, username, password);
                 Statement statement = connection.createStatement()) {
                
                // 创建数据库（如果不存在）
                String sql = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci";
                statement.executeUpdate(sql);
                log.info("已确认数据库 {} 存在或已创建", databaseName);
                
            }
        } catch (SQLException e) {
            log.error("尝试创建数据库时发生错误: {}", e.getMessage(), e);
            // 不抛出异常，让后续步骤继续执行
        }
    }
} 