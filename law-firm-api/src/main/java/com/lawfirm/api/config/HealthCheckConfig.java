package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康检查配置
 * 在应用启动时检查关键组件的可用性
 */
@Slf4j
@Configuration
public class HealthCheckConfig implements CommandLineRunner {

    @Autowired(required = false)
    private DataSource dataSource;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void run(String... args) {
        log.info("开始进行系统健康检查...");
        List<String> errors = new ArrayList<>();

        // 检查MySQL连接
        if (dataSource != null) {
            try {
                log.info("检查MySQL连接...");
                String version = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
                log.info("MySQL连接成功，版本：{}", version);
            } catch (Exception e) {
                String error = "MySQL连接失败：" + e.getMessage();
                log.error(error, e);
                errors.add(error);
            }
        } else {
            log.warn("未配置MySQL数据源，跳过MySQL连接检查");
        }

        // 检查Redis连接
        if (redisConnectionFactory != null) {
            try {
                log.info("检查Redis连接...");
                redisConnectionFactory.getConnection().ping();
                log.info("Redis连接成功");
            } catch (Exception e) {
                String error = "Redis连接失败：" + e.getMessage();
                log.error(error, e);
                errors.add(error);
            }
        } else {
            log.warn("未配置Redis连接工厂，跳过Redis连接检查");
        }

        // 输出健康检查结果
        if (errors.isEmpty()) {
            log.info("系统健康检查完成，所有组件连接正常");
        } else {
            log.error("系统健康检查完成，但存在以下问题：");
            for (int i = 0; i < errors.size(); i++) {
                log.error("  {}. {}", i + 1, errors.get(i));
            }
            // 根据实际需求，如果存在严重问题可以选择退出应用
            // System.exit(1);
        }
    }
} 