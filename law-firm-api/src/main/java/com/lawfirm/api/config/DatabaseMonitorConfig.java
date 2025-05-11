package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库监控配置
 * 
 * 提供数据库健康检查和性能监控功能
 * 要求项目引入spring-boot-starter-actuator依赖
 */
@Slf4j
@Configuration("databaseMonitorConfig")
@ConditionalOnClass(name = "org.springframework.boot.actuate.health.HealthIndicator")
@ConditionalOnProperty(name = "law-firm.common.data.monitoring.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseMonitorConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 提供数据库健康检查器
     */
    @Bean("databaseHealthIndicator")
    @ConditionalOnEnabledHealthIndicator("db")
    public HealthIndicator databaseHealthIndicator() {
        return () -> {
            try {
                // 执行简单查询测试连接
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                Map<String, Object> details = new HashMap<>();
                
                // 检查数据库连接
                try (Connection connection = dataSource.getConnection()) {
                    details.put("database", connection.getCatalog());
                    details.put("driver", connection.getMetaData().getDriverName());
                    details.put("url", connection.getMetaData().getURL());
                    details.put("username", connection.getMetaData().getUserName());
                }
                
                // 检查活动连接
                if (dataSource instanceof com.zaxxer.hikari.HikariDataSource) {
                    com.zaxxer.hikari.HikariDataSource hikariDS = (com.zaxxer.hikari.HikariDataSource) dataSource;
                    details.put("active-connections", hikariDS.getHikariPoolMXBean().getActiveConnections());
                    details.put("idle-connections", hikariDS.getHikariPoolMXBean().getIdleConnections());
                    details.put("total-connections", hikariDS.getHikariPoolMXBean().getTotalConnections());
                    details.put("threads-awaiting-connection", hikariDS.getHikariPoolMXBean().getThreadsAwaitingConnection());
                }
                
                // 验证可以执行查询
                Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                if (result != null && result == 1) {
                    details.put("query-status", "SUCCESS");
                    return Health.up().withDetails(details).build();
                } else {
                    details.put("query-status", "FAILED");
                    return Health.down().withDetails(details).build();
                }
            } catch (SQLException e) {
                return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("sql-state", e.getSQLState())
                    .withDetail("vendor-code", e.getErrorCode())
                    .build();
            } catch (Exception e) {
                return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
            }
        };
    }
    
    /**
     * 数据库性能统计
     */
    @Bean("databasePerformanceMonitor")
    @ConditionalOnProperty(name = "law-firm.common.data.monitoring.performance.enabled", havingValue = "true", matchIfMissing = false)
    public DatabasePerformanceMonitor databasePerformanceMonitor() {
        return new DatabasePerformanceMonitor(dataSource);
    }
    
    /**
     * 数据库性能监控类
     */
    public static class DatabasePerformanceMonitor {
        private final DataSource dataSource;
        
        public DatabasePerformanceMonitor(DataSource dataSource) {
            this.dataSource = dataSource;
            log.info("数据库性能监控已启用");
        }
        
        /**
         * 获取连接池状态
         */
        public Map<String, Object> getConnectionPoolStatus() {
            Map<String, Object> status = new HashMap<>();
            
            if (dataSource instanceof com.zaxxer.hikari.HikariDataSource) {
                com.zaxxer.hikari.HikariDataSource hikariDS = (com.zaxxer.hikari.HikariDataSource) dataSource;
                status.put("active-connections", hikariDS.getHikariPoolMXBean().getActiveConnections());
                status.put("idle-connections", hikariDS.getHikariPoolMXBean().getIdleConnections());
                status.put("total-connections", hikariDS.getHikariPoolMXBean().getTotalConnections());
                status.put("threads-awaiting-connection", hikariDS.getHikariPoolMXBean().getThreadsAwaitingConnection());
                status.put("pool-name", hikariDS.getPoolName());
            }
            
            return status;
        }
    }
} 