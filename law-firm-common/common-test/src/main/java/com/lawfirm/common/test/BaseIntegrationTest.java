package com.lawfirm.common.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(SpringExtension.class)
// @SpringBootTest  // 移除该注解，避免重复加载
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    // 集成测试的通用配置和工具方法
    protected void clearDatabase() {
        // 简单示例：清空所有表（实际可根据业务表名调整）
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0;");
        jdbcTemplate.query("SHOW TABLES", (rs) -> {
            while (rs.next()) {
                String table = rs.getString(1);
                jdbcTemplate.execute("TRUNCATE TABLE " + table + ";");
            }
            return null;
        });
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1;");
    }

    protected void setupTestData() {
        // 示例：插入一条测试数据（实际可根据业务表调整）
        // jdbcTemplate.update("INSERT INTO user (id, name) VALUES (?, ?)", 1, "test");
    }
} 