package com.lawfirm.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接检查工具类
 * <p>
 * 提供数据库连接检查功能，支持连接验证和错误诊断
 */
@Slf4j
public class DatabaseConnectionChecker {

    /**
     * 数据库连接参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConnectionParams {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        
        /**
         * 从环境中构建连接参数
         *
         * @param env Spring环境
         * @return 连接参数
         */
        public static ConnectionParams fromEnvironment(Environment env) {
            return ConnectionParams.builder()
                    .url(env.getProperty("spring.datasource.url"))
                    .username(env.getProperty("spring.datasource.username"))
                    .password(env.getProperty("spring.datasource.password"))
                    .driverClassName(env.getProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"))
                    .build();
        }
        
        /**
         * 创建默认连接参数对象
         *
         * @return 默认连接参数
         */
        public static ConnectionParams defaultParams() {
            return ConnectionParams.builder()
                    .url("jdbc:mysql://localhost:3306/law_firm?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai")
                    .username("root")
                    .password("")
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .build();
        }
    }

    /**
     * 连接检查结果
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckResult {
        private boolean success;
        private String message;
        private String errorCode;
        private String errorDetails;
        
        /**
         * 创建成功结果
         *
         * @return 成功结果
         */
        public static CheckResult success() {
            return CheckResult.builder()
                    .success(true)
                    .message("数据库连接成功")
                    .build();
        }
        
        /**
         * 创建失败结果
         *
         * @param errorMessage 错误消息
         * @param errorCode    错误代码
         * @param details      详细信息
         * @return 失败结果
         */
        public static CheckResult failure(String errorMessage, String errorCode, String details) {
            return CheckResult.builder()
                    .success(false)
                    .message(errorMessage)
                    .errorCode(errorCode)
                    .errorDetails(details)
                    .build();
        }
    }

    /**
     * 检查数据库连接
     *
     * @param params 连接参数
     * @return 检查结果
     */
    public static CheckResult checkConnection(ConnectionParams params) {
        log.info("检查数据库连接: {}", params.getUrl());
        
        try {
            // 加载驱动
            Class.forName(params.getDriverClassName());
            
            // 尝试连接
            try (Connection connection = DriverManager.getConnection(
                    params.getUrl(),
                    params.getUsername(),
                    params.getPassword())) {
                
                if (connection.isValid(5)) {
                    log.info("数据库连接成功");
                    return CheckResult.success();
                } else {
                    log.error("数据库连接无效");
                    return CheckResult.failure(
                            "数据库连接无效",
                            "CONNECTION_INVALID",
                            "连接已建立但无法验证有效性，可能是数据库服务器负载过高"
                    );
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动未找到: {}", e.getMessage());
            return CheckResult.failure(
                    "数据库驱动未找到",
                    "DRIVER_NOT_FOUND",
                    "未找到JDBC驱动: " + params.getDriverClassName()
            );
        } catch (SQLException e) {
            log.error("数据库连接失败: {}, 错误代码: {}", e.getMessage(), e.getErrorCode());
            return handleSQLException(e);
        } catch (Exception e) {
            log.error("连接数据库时发生未知错误: {}", e.getMessage());
            return CheckResult.failure(
                    "连接数据库时发生未知错误",
                    "UNKNOWN_ERROR",
                    e.getMessage()
            );
        }
    }
    
    /**
     * 处理SQL异常，提供更友好的错误提示
     *
     * @param e SQL异常
     * @return 检查结果
     */
    private static CheckResult handleSQLException(SQLException e) {
        String sqlState = e.getSQLState();
        int errorCode = e.getErrorCode();
        
        if (sqlState != null) {
            switch (sqlState.substring(0, 2)) {
                case "08":
                    return CheckResult.failure(
                            "无法连接到数据库服务器",
                            "CONNECTION_FAILED",
                            "请检查数据库服务器是否已启动且网络可达: " + e.getMessage()
                    );
                case "28":
                    return CheckResult.failure(
                            "用户名或密码错误",
                            "AUTHENTICATION_FAILED",
                            "请检查数据库用户名和密码是否正确: " + e.getMessage()
                    );
                default:
                    break;
            }
        }
        
        // MySQL specific error codes
        if (errorCode == 1049) {
            return CheckResult.failure(
                    "数据库不存在",
                    "DATABASE_NOT_FOUND",
                    "请确认数据库名称是否正确，或者使用自动创建数据库的连接URL: " + e.getMessage()
            );
        }
        
        // Default error handling
        return CheckResult.failure(
                "数据库连接失败",
                "SQL_ERROR_" + errorCode,
                e.getMessage()
        );
    }
    
    /**
     * 诊断数据库连接问题并提供解决方案
     *
     * @param result 检查结果
     * @return 诊断建议
     */
    public static String diagnose(CheckResult result) {
        if (result.isSuccess()) {
            return "数据库连接正常，无需修复。";
        }
        
        StringBuilder suggestion = new StringBuilder();
        suggestion.append("数据库连接问题: ").append(result.getMessage()).append("\n");
        suggestion.append("错误代码: ").append(result.getErrorCode()).append("\n");
        suggestion.append("详细信息: ").append(result.getErrorDetails()).append("\n\n");
        suggestion.append("建议解决方案:\n");
        
        switch (result.getErrorCode()) {
            case "DRIVER_NOT_FOUND":
                suggestion.append("1. 确保已在pom.xml中添加MySQL驱动依赖\n");
                suggestion.append("2. 检查driver-class-name配置是否正确\n");
                suggestion.append("3. 尝试重新构建项目以更新依赖");
                break;
            case "CONNECTION_FAILED":
                suggestion.append("1. 确认MySQL服务已启动\n");
                suggestion.append("2. 检查数据库URL中的主机和端口是否正确\n");
                suggestion.append("3. 确认网络连接正常，防火墙未阻止连接");
                break;
            case "AUTHENTICATION_FAILED":
                suggestion.append("1. 检查数据库用户名和密码是否正确\n");
                suggestion.append("2. 确认该用户有权限访问指定的数据库\n");
                suggestion.append("3. 尝试在MySQL客户端使用相同凭据登录验证");
                break;
            case "DATABASE_NOT_FOUND":
                suggestion.append("1. 确认数据库名称是否正确\n");
                suggestion.append("2. 在连接URL中添加createDatabaseIfNotExist=true参数\n");
                suggestion.append("3. 或者手动创建数据库: CREATE DATABASE law_firm CHARACTER SET utf8mb4");
                break;
            default:
                suggestion.append("1. 检查数据库配置参数\n");
                suggestion.append("2. 确认数据库服务器状态\n");
                suggestion.append("3. 检查应用日志获取更多详细信息");
                break;
        }
        
        return suggestion.toString();
    }
} 