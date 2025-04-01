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
            boolean isSystemExists = isColumnExists("sys_config", "is_system");
            
            if (!isSystemExists) {
                log.info("添加is_system字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD is_system BOOLEAN NOT NULL DEFAULT 0"
                );
                log.info("is_system字段添加成功");
            } else {
                log.info("is_system字段已存在，无需修复");
            }
            
            // 检查sys_config表是否存在version字段
            boolean versionExists = isColumnExists("sys_config", "version");
            
            if (!versionExists) {
                log.info("添加version字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD version INT DEFAULT 0 COMMENT '版本号'"
                );
                log.info("version字段添加成功");
            } else {
                log.info("version字段已存在，无需修复");
            }
            
            // 检查sys_config表是否存在status字段
            boolean statusExists = isColumnExists("sys_config", "status");
            
            if (!statusExists) {
                log.info("添加status字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD status INT DEFAULT 0 COMMENT '状态（0-启用，1-禁用）'"
                );
                log.info("status字段添加成功");
            } else {
                log.info("status字段已存在，无需修复");
            }
            
            // 检查sys_config表是否存在sort字段
            boolean sortExists = isColumnExists("sys_config", "sort");
            
            if (!sortExists) {
                log.info("添加sort字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD sort INT DEFAULT 0 COMMENT '排序号'"
                );
                log.info("sort字段添加成功");
            } else {
                log.info("sort字段已存在，无需修复");
            }
            
            // 检查sys_config表是否存在deleted字段
            boolean deletedExists = isColumnExists("sys_config", "deleted");
            
            if (!deletedExists) {
                log.info("添加deleted字段到sys_config表");
                jdbcTemplate.execute(
                    "ALTER TABLE sys_config ADD deleted INT DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）'"
                );
                log.info("deleted字段添加成功");
            } else {
                log.info("deleted字段已存在，无需修复");
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