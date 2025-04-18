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
 * 只有在lawfirm.database.enabled=true或未设置时才启用
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceConfig {
    
    @Autowired
    private Environment environment;

    /**
     * 创建主数据源
     * 优先使用spring.datasource标准配置，兼容lawfirm.database配置
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        log.info("数据库服务已启用，创建数据源");
        
        // 优先使用标准的spring.datasource配置
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        
        // 如果标准配置不存在，则使用自定义配置
        if (url == null) {
            url = environment.getProperty("SPRING_DATASOURCE_URL", 
                    environment.getProperty("lawfirm.database.url", 
                    "jdbc:mysql://localhost:3306/law_firm?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true"));
        }
        
        if (username == null) {
            username = environment.getProperty("SPRING_DATASOURCE_USERNAME", 
                    environment.getProperty("lawfirm.database.username", "root"));
        }
        
        if (password == null) {
            password = environment.getProperty("SPRING_DATASOURCE_PASSWORD", 
                    environment.getProperty("lawfirm.database.password", ""));
        }
        
        log.info("创建数据源：{}, username: {}", url, username);
        
        // 提取数据库名称
        String databaseName = extractDatabaseName(url);
        // 尝试创建数据库
        createDatabaseIfNotExists(databaseName, username, password);
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"));
        
        // 连接池配置 - 优先使用标准配置
        Integer minIdle = environment.getProperty("spring.datasource.hikari.minimum-idle", Integer.class);
        if (minIdle == null) {
            minIdle = environment.getProperty("lawfirm.database.pool.min-idle", Integer.class, 5);
        }
        config.setMinimumIdle(minIdle);
        
        Integer maxPoolSize = environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class);
        if (maxPoolSize == null) {
            maxPoolSize = environment.getProperty("lawfirm.database.pool.max-pool-size", Integer.class, 20);
        }
        config.setMaximumPoolSize(maxPoolSize);
        
        Long connTimeout = environment.getProperty("spring.datasource.hikari.connection-timeout", Long.class);
        if (connTimeout == null) {
            connTimeout = environment.getProperty("lawfirm.database.pool.connection-timeout", Long.class, 30000L);
        }
        config.setConnectionTimeout(connTimeout);
        
        Long idleTimeout = environment.getProperty("spring.datasource.hikari.idle-timeout", Long.class);
        if (idleTimeout == null) {
            idleTimeout = environment.getProperty("lawfirm.database.pool.idle-timeout", Long.class, 600000L);
        }
        config.setIdleTimeout(idleTimeout);
        
        Long maxLifetime = environment.getProperty("spring.datasource.hikari.max-lifetime", Long.class);
        if (maxLifetime == null) {
            maxLifetime = environment.getProperty("lawfirm.database.pool.max-lifetime", Long.class, 1800000L);
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
    private void createDatabaseIfNotExists(String databaseName, String username, String password) {
        // 构建不包含数据库名的连接URL
        String baseUrl = "jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        
        try (Connection connection = java.sql.DriverManager.getConnection(baseUrl, username, password);
             Statement statement = connection.createStatement()) {
            
            // 创建数据库（如果不存在）
            String sql = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci";
            statement.executeUpdate(sql);
            log.info("已确认数据库 {} 存在或已创建", databaseName);
            
        } catch (SQLException e) {
            log.error("尝试创建数据库时发生错误", e);
            // 不抛出异常，让后续步骤继续执行，可能会因为数据库不存在而失败
        }
    }
} 