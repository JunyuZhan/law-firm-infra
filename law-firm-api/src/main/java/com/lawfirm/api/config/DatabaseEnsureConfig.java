package com.lawfirm.api.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库预检和日志目录初始化配置
 * 确保在应用启动前数据库连接可用和日志目录存在
 */
@Slf4j
@Configuration
@Order(0) // 最高优先级，确保在其他组件之前初始化
public class DatabaseEnsureConfig {

    @Autowired
    private Environment environment;
    
    /**
     * 在应用启动前确保数据库和日志目录已准备好
     */
    @Bean(name = "databaseInitEnsurer")
    public boolean ensureDatabaseAndLogDirectories() {
        log.info("执行预启动检查...");
        
        // 1. 确保日志目录存在
        ensureLogDirectories();
        
        // 2. 检查数据库连接
        testDatabaseConnection();
        
        return true;
    }
    
    /**
     * 确保日志目录存在
     * 由于移除了logback的prudent模式，这个方法变得更加重要
     */
    private void ensureLogDirectories() {
        try {
            // 获取日志路径
            String logPath = environment.getProperty("logging.file.path", "logs");
            String logFileName = environment.getProperty("logging.file.name", "law-firm-api.log");
            
            // 如果日志文件名包含路径，提取目录部分
            if (logFileName != null && (logFileName.contains("/") || logFileName.contains("\\"))) {
                File logFile = new File(logFileName);
                String parent = logFile.getParent();
                if (parent != null) {
                    logPath = parent;
                }
            }
            
            // 创建标准日志目录
            createDirectoryIfNotExists(logPath);
            
            // 创建默认日志位置（以防万一）
            createDirectoryIfNotExists("logs");
            createDirectoryIfNotExists("logs/archive");
            
            // 创建基于环境属性的日志路径
            String tmpDir = System.getProperty("java.io.tmpdir");
            if (tmpDir != null) {
                createDirectoryIfNotExists(new File(tmpDir, "logs").getPath());
            }
            
            // 尝试解析LOG_FILE_NAME中可能的目录
            String resolvedLogPath = environment.resolvePlaceholders(
                    "${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-/tmp}}}/logs/law-firm-api.log}");
            if (resolvedLogPath != null && resolvedLogPath.contains("/")) {
                File resolvedFile = new File(resolvedLogPath);
                String resolvedDir = resolvedFile.getParent();
                if (resolvedDir != null) {
                    createDirectoryIfNotExists(resolvedDir);
                }
            }
        } catch (Exception e) {
            log.error("确保日志目录存在时发生错误: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 如果目录不存在，则创建它
     */
    private void createDirectoryIfNotExists(String path) {
        if (path == null || path.trim().isEmpty()) {
            return;
        }
        
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (created) {
                    log.info("创建日志目录: {}", dir.getAbsolutePath());
                } else {
                    log.warn("无法创建日志目录: {}", dir.getAbsolutePath());
                }
            } else if (dir.isDirectory()) {
                log.info("日志目录已存在: {}", dir.getAbsolutePath());
            } else {
                log.warn("路径存在但不是目录: {}", dir.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("创建目录时发生错误 {}: {}", path, e.getMessage());
        }
    }
    
    /**
     * 测试数据库连接
     */
    private void testDatabaseConnection() {
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
        
        // 获取或构建JDBC URL
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", 
                    host, port, database);
        }
        
        log.info("测试数据库连接: {}", jdbcUrl);
        
        // 测试连接
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                if (connection.isValid(5)) {
                    log.info("数据库连接测试成功");
                } else {
                    log.error("数据库连接无效");
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("MySQL驱动加载失败: {}", e.getMessage());
        } catch (SQLException e) {
            log.error("数据库连接测试失败: {}", e.getMessage());
            log.info("将尝试创建数据库 {} 并重新连接", database);
            
            // 尝试创建数据库并重新连接
            tryCreateDatabase(host, port, database, username, password);
        }
    }
    
    /**
     * 尝试创建数据库
     */
    private void tryCreateDatabase(String host, String port, String database, String username, String password) {
        String baseUrl = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", 
                host, port);
        
        try {
            try (Connection conn = DriverManager.getConnection(baseUrl, username, password)) {
                conn.createStatement().executeUpdate(
                        "CREATE DATABASE IF NOT EXISTS `" + database + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
                log.info("数据库 {} 已创建或已存在", database);
            }
        } catch (SQLException e) {
            log.error("创建数据库失败: {}", e.getMessage());
        }
    }
} 