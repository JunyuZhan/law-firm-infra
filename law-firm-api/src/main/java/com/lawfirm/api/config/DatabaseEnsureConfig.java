package com.lawfirm.api.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            // 获取主要日志路径配置
            String logPath = environment.getProperty("logging.file.path");
            String logFileName = environment.getProperty("logging.file.name");
            
            List<String> directoriesToCreate = new ArrayList<>();
            
            // 处理日志文件配置
            if (logFileName != null) {
                // 如果配置了完整的日志文件路径，提取目录部分
                File logFile = new File(logFileName);
                String parent = logFile.getParent();
                if (parent != null) {
                    directoriesToCreate.add(parent);
                }
            }
            
            // 处理日志路径配置
            if (logPath != null) {
                directoriesToCreate.add(logPath);
            } else {
                // 如果没有配置日志路径，确保默认日志目录存在
                directoriesToCreate.add("logs");
            }
            
            // 创建所有需要的目录
            for (String dir : directoriesToCreate) {
                createDirectoryIfNotExists(dir);
            }
            
            // 创建归档目录（如果主目录存在）
            for (String dir : directoriesToCreate) {
                if (new File(dir).exists()) {
                    createDirectoryIfNotExists(new File(dir, "archive").getPath());
                    break; // 只在第一个存在的目录中创建归档目录
                }
            }
            
            log.info("日志目录初始化完成");
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
        // 优先使用标准数据源配置
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        
        // 如果标准配置中没有值，再尝试从环境变量获取
        if (username == null) {
            username = environment.getProperty("MYSQL_USERNAME", "root");
        }
        
        if (password == null) {
            password = environment.getProperty("MYSQL_PASSWORD", "");
        }
        
        // 获取数据库名称和主机信息（用于可能的数据库创建）
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
        
        // 构建JDBC URL（如果未配置）
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", 
                    host, port, database);
        }
        
        log.info("测试数据库连接: {}", jdbcUrl);
        
        // 测试连接
        boolean connectionSuccess = testConnection(jdbcUrl, username, password);
        
        // 如果连接失败，尝试创建数据库
        if (!connectionSuccess) {
            log.info("将尝试创建数据库 {} 并重新连接", database);
            boolean created = tryCreateDatabase(host, port, database, username, password);
            
            // 如果创建成功，再次测试连接
            if (created) {
                log.info("重新测试数据库连接...");
                testConnection(jdbcUrl, username, password);
            }
        }
    }
    
    /**
     * 测试数据库连接
     * 
     * @return 连接是否成功
     */
    private boolean testConnection(String jdbcUrl, String username, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            if (connection.isValid(5)) {
                log.info("数据库连接测试成功");
                return true;
            } else {
                log.error("数据库连接无效");
                return false;
            }
        } catch (ClassNotFoundException e) {
            log.error("MySQL驱动加载失败: {}", e.getMessage());
            return false;
        } catch (SQLException e) {
            log.error("数据库连接测试失败: {}", e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    log.debug("测试连接已关闭");
                } catch (SQLException e) {
                    log.warn("关闭测试连接时发生错误: {}", e.getMessage());
                }
            }
        }
    }
    
    /**
     * 尝试创建数据库
     * 
     * @return 数据库是否创建成功
     */
    private boolean tryCreateDatabase(String host, String port, String database, String username, String password) {
        String baseUrl = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", 
                host, port);
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(baseUrl, username, password);
            conn.createStatement().executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS `" + database + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
            log.info("数据库 {} 已创建或已存在", database);
            return true;
        } catch (SQLException e) {
            log.error("创建数据库失败: {}", e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    log.debug("数据库创建连接已关闭");
                } catch (SQLException e) {
                    log.warn("关闭数据库创建连接时发生错误: {}", e.getMessage());
                }
            }
        }
    }
} 