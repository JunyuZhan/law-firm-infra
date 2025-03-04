package com.lawfirm.common.security.crypto;

import com.lawfirm.common.security.crypto.impl.SensitiveDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 数据脱敏服务测试类
 */
public class SensitiveDataServiceTest {

    private SensitiveDataService sensitiveDataService;

    @BeforeEach
    public void setUp() {
        sensitiveDataService = new SensitiveDataServiceImpl();
    }

    @Test
    public void testMaskPhoneNumber() {
        // 测试正常手机号
        assertEquals("134****8888", sensitiveDataService.maskPhoneNumber("13412348888"));
        
        // 测试空值
        assertNull(sensitiveDataService.maskPhoneNumber(null));
        assertEquals("", sensitiveDataService.maskPhoneNumber(""));
        
        // 测试异常长度
        assertEquals("123", sensitiveDataService.maskPhoneNumber("123"));
    }

    @Test
    public void testMaskIdCard() {
        // 测试正常身份证号
        assertEquals("310101********1234", sensitiveDataService.maskIdCard("310101199001011234"));
        
        // 测试空值
        assertNull(sensitiveDataService.maskIdCard(null));
        assertEquals("", sensitiveDataService.maskIdCard(""));
        
        // 测试异常长度
        assertEquals("1*3", sensitiveDataService.maskIdCard("123"));
    }

    @Test
    public void testMaskBankCard() {
        // 测试正常银行卡号
        assertEquals("************1234", sensitiveDataService.maskBankCard("6222021234561234"));
        
        // 测试空值
        assertNull(sensitiveDataService.maskBankCard(null));
        assertEquals("", sensitiveDataService.maskBankCard(""));
        
        // 测试异常长度
        assertEquals("***", sensitiveDataService.maskBankCard("123"));
    }

    @Test
    public void testMaskEmail() {
        // 测试正常邮箱
        assertEquals("t***t@example.com", sensitiveDataService.maskEmail("test@example.com"));
        
        // 测试空值
        assertNull(sensitiveDataService.maskEmail(null));
        assertEquals("", sensitiveDataService.maskEmail(""));
        
        // 测试异常格式
        assertEquals("test", sensitiveDataService.maskEmail("test"));
    }

    @Test
    public void testMaskName() {
        // 测试中文姓名
        assertEquals("张**", sensitiveDataService.maskName("张三丰"));
        
        // 测试英文姓名
        assertEquals("J****", sensitiveDataService.maskName("James"));
        
        // 测试空值
        assertNull(sensitiveDataService.maskName(null));
        assertEquals("", sensitiveDataService.maskName(""));
        
        // 测试短名
        assertEquals("张*", sensitiveDataService.maskName("张三"));
        assertEquals("李", sensitiveDataService.maskName("李"));
    }

    @Test
    public void testMask() {
        // 测试自定义脱敏
        assertEquals("Hello*****World", sensitiveDataService.mask("Hello12345World", 5, 5, '*'));
        
        // 测试空值
        assertNull(sensitiveDataService.mask(null, 1, 1, '*'));
        assertEquals("", sensitiveDataService.mask("", 1, 1, '*'));
        
        // 测试边界情况
        assertEquals("12*4", sensitiveDataService.mask("1234", 2, 1, '*'));
        assertEquals("1*34", sensitiveDataService.mask("1234", 1, 2, '*'));
        assertEquals("**34", sensitiveDataService.mask("1234", 0, 2, '*'));
        assertEquals("12**", sensitiveDataService.mask("1234", 2, 0, '*'));
        
        // 测试异常参数
        assertEquals("*234", sensitiveDataService.mask("1234", -1, 3, '*'));
        assertEquals("123*", sensitiveDataService.mask("1234", 3, -1, '*'));
    }
} 