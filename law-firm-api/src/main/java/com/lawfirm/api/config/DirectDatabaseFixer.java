package com.lawfirm.api.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * 直接数据库修复工具
 * <p>
 * 通过PostConstruct在所有Bean初始化后，但在应用完全启动前执行
 * </p>
 */
@Component
public class DirectDatabaseFixer {
    
    private static final Logger log = LoggerFactory.getLogger(DirectDatabaseFixer.class);
    
    @Autowired
    private DataSource dataSource;
    
    @PostConstruct
    public void fixDatabase() {
        log.info("开始直接修复数据库结构...");
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // 检查字段是否存在
            try {
                conn.createStatement().executeQuery("SELECT is_system FROM sys_config LIMIT 1");
                log.info("is_system字段已存在，无需修复");
            } catch (Exception e) {
                // 字段不存在，添加字段
                log.info("is_system字段不存在，开始添加...");
                String sql = "ALTER TABLE sys_config ADD is_system BOOLEAN NOT NULL DEFAULT 0";
                stmt.execute(sql);
                log.info("成功添加is_system字段到sys_config表");
            }
            
            log.info("数据库修复完成");
        } catch (Exception e) {
            log.error("数据库修复过程中发生错误: {}", e.getMessage(), e);
        }
    }
} 