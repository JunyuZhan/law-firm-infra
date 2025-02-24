package com.lawfirm.common.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.lawfirm.common.util.config.TestConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
public abstract class BaseUtilTest {
    
    /**
     * 生成指定长度的随机字符串
     */
    protected String randomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    
    /**
     * 生成指定范围内的随机整数
     */
    protected int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    
    /**
     * 生成测试用的临时文件名
     */
    protected String randomFileName() {
        return "test_" + System.currentTimeMillis() + "_" + randomString(5);
    }
} 