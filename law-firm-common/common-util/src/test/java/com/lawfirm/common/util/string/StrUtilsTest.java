package com.lawfirm.common.util.string;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrUtilsTest {

    @Test
    void testCamelToUnderscore() {
        // 测试正常情况
        assertEquals("user_name", StrUtils.camelToUnderscore("userName"));
        assertEquals("user_name_info", StrUtils.camelToUnderscore("userNameInfo"));
        assertEquals("abc", StrUtils.camelToUnderscore("abc"));
        
        // 测试边界情况
        assertNull(StrUtils.camelToUnderscore(null));
        assertEquals("", StrUtils.camelToUnderscore(""));
        assertEquals("a", StrUtils.camelToUnderscore("a"));
    }

    @Test
    void testUnderscoreToCamel() {
        // 测试正常情况
        assertEquals("userName", StrUtils.underscoreToCamel("user_name"));
        assertEquals("userNameInfo", StrUtils.underscoreToCamel("user_name_info"));
        assertEquals("abc", StrUtils.underscoreToCamel("abc"));
        
        // 测试边界情况
        assertNull(StrUtils.underscoreToCamel(null));
        assertEquals("", StrUtils.underscoreToCamel(""));
        assertEquals("a", StrUtils.underscoreToCamel("a"));
    }

    @Test
    void testMaskPhone() {
        // 测试正常情况
        assertEquals("138****1234", StrUtils.maskPhone("13812341234"));
        assertEquals("020****5678", StrUtils.maskPhone("02012345678"));
        
        // 测试边界情况
        assertNull(StrUtils.maskPhone(null));
        assertEquals("", StrUtils.maskPhone(""));
        assertEquals("123456", StrUtils.maskPhone("123456")); // 长度小于7的情况
    }

    @Test
    void testMaskEmail() {
        // 测试正常情况
        assertEquals("t***t@example.com", StrUtils.maskEmail("test@example.com"));
        assertEquals("u***r@domain.com", StrUtils.maskEmail("user@domain.com"));
        
        // 测试短用户名
        assertEquals("**@domain.com", StrUtils.maskEmail("ab@domain.com"));
        assertEquals("*@domain.com", StrUtils.maskEmail("a@domain.com"));
        
        // 测试边界情况
        assertNull(StrUtils.maskEmail(null));
        assertEquals("", StrUtils.maskEmail(""));
        assertEquals("invalid", StrUtils.maskEmail("invalid")); // 不包含@的情况
    }

    @Test
    void testIsBlank() {
        // 测试空字符串
        assertTrue(StrUtils.isBlank(null));
        assertTrue(StrUtils.isBlank(""));
        assertTrue(StrUtils.isBlank(" "));
        assertTrue(StrUtils.isBlank("\t"));
        assertTrue(StrUtils.isBlank("\n"));
        
        // 测试非空字符串
        assertFalse(StrUtils.isBlank("a"));
        assertFalse(StrUtils.isBlank(" a "));
    }

    @Test
    void testIsNotBlank() {
        // 测试非空字符串
        assertTrue(StrUtils.isNotBlank("a"));
        assertTrue(StrUtils.isNotBlank(" a "));
        
        // 测试空字符串
        assertFalse(StrUtils.isNotBlank(null));
        assertFalse(StrUtils.isNotBlank(""));
        assertFalse(StrUtils.isNotBlank(" "));
        assertFalse(StrUtils.isNotBlank("\t"));
        assertFalse(StrUtils.isNotBlank("\n"));
    }
} 