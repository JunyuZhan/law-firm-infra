package com.lawfirm.model.document;

import com.lawfirm.model.document.config.TestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
public abstract class BaseTest {
    // 可以添加通用的测试辅助方法
    protected void cleanupTestFiles() {
        // 清理测试文件的逻辑
    }
    
    protected String generateTestFilePath(String fileName) {
        return "./test-storage/" + fileName;
    }
} 