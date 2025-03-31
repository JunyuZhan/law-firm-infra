package com.lawfirm.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库初始化器
 * <p>
 * 用于解决系统启动时的数据库字段缺失问题
 * </p>
 */
@Slf4j
@Component
@Order(0) // 确保在所有其他Runner之前执行
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) {
        log.info("开始执行数据库修复...");
        
        try {
            // 检查sys_config表是否存在is_system字段
            boolean columnExists = isColumnExists("sys_config", "is_system");
            
            if (!columnExists) {
                log.info("添加is_system字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD is_system BOOLEAN NOT NULL DEFAULT 0"
                );
                log.info("is_system字段添加成功");
            } else {
                log.info("is_system字段已存在，无需修复");
            }
            
            log.info("数据库修复完成");
        } catch (Exception e) {
            log.error("数据库修复过程中发生错误: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 检查表是否存在指定列
     */
    private boolean isColumnExists(String tableName, String columnName) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.columns " +
                         "WHERE table_name = ? AND column_name = ?";
            
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tableName, columnName);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("检查列是否存在时发生错误: {}", e.getMessage(), e);
            return false;
        }
    }
} 