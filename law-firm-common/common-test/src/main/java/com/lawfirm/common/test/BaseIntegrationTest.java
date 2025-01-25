package com.lawfirm.common.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {
    // 集成测试的通用配置和工具方法
    protected void clearDatabase() {
        // TODO: 实现数据库清理逻辑
    }

    protected void setupTestData() {
        // TODO: 实现测试数据准备逻辑
    }
} 