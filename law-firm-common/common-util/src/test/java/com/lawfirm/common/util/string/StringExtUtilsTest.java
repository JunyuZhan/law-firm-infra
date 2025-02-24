package com.lawfirm.common.util.string;

import com.lawfirm.common.util.BaseUtilTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringExtUtilsTest extends BaseUtilTest {

    @Test
    void truncate_ShouldReturnOriginalString_WhenLengthLessThanMax() {
        // 测试字符串长度小于最大长度的情况
        String input = "Hello";
        assertEquals(input, StringExtUtils.truncate(input, 10));
        
        // 测试字符串长度等于最大长度的情况
        assertEquals(input, StringExtUtils.truncate(input, 5));
        
        // 测试空字符串
        assertEquals("", StringExtUtils.truncate("", 5));
        
        // 测试null
        assertNull(StringExtUtils.truncate(null, 5));
    }
    
    @Test
    void truncate_ShouldTruncateString_WhenLengthExceedsMax() {
        String input = "Hello, World!";
        assertEquals("Hello...", StringExtUtils.truncate(input, 5));
        assertEquals("Hello, W...", StringExtUtils.truncate(input, 8));
    }

    @Test
    void removeAllWhitespace_ShouldRemoveAllSpaces() {
        // 测试正常情况
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace("Hello World"));
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace("Hello  World"));
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace(" Hello World "));
        
        // 测试特殊空白字符
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace("Hello\tWorld"));
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace("Hello\nWorld"));
        assertEquals("HelloWorld", StringExtUtils.removeAllWhitespace("Hello\r\nWorld"));
        
        // 测试边界情况
        assertNull(StringExtUtils.removeAllWhitespace(null));
        assertEquals("", StringExtUtils.removeAllWhitespace(""));
        assertEquals("", StringExtUtils.removeAllWhitespace(" "));
    }

    @Test
    void capitalizeFirstLetter_ShouldCapitalizeFirstLetter() {
        // 测试正常情况
        assertEquals("Hello", StringExtUtils.capitalizeFirstLetter("hello"));
        assertEquals("World", StringExtUtils.capitalizeFirstLetter("world"));
        
        // 测试已经是大写的情况
        assertEquals("Hello", StringExtUtils.capitalizeFirstLetter("Hello"));
        
        // 测试特殊情况
        assertEquals("123abc", StringExtUtils.capitalizeFirstLetter("123abc"));
        assertEquals(" hello", StringExtUtils.capitalizeFirstLetter(" hello"));
        
        // 测试边界情况
        assertNull(StringExtUtils.capitalizeFirstLetter(null));
        assertEquals("", StringExtUtils.capitalizeFirstLetter(""));
        assertEquals("A", StringExtUtils.capitalizeFirstLetter("a"));
    }

    @Test
    void uncapitalizeFirstLetter_ShouldUncapitalizeFirstLetter() {
        // 测试正常情况
        assertEquals("hello", StringExtUtils.uncapitalizeFirstLetter("Hello"));
        assertEquals("world", StringExtUtils.uncapitalizeFirstLetter("World"));
        
        // 测试已经是小写的情况
        assertEquals("hello", StringExtUtils.uncapitalizeFirstLetter("hello"));
        
        // 测试特殊情况
        assertEquals("123abc", StringExtUtils.uncapitalizeFirstLetter("123abc"));
        assertEquals(" Hello", StringExtUtils.uncapitalizeFirstLetter(" Hello"));
        
        // 测试边界情况
        assertNull(StringExtUtils.uncapitalizeFirstLetter(null));
        assertEquals("", StringExtUtils.uncapitalizeFirstLetter(""));
        assertEquals("a", StringExtUtils.uncapitalizeFirstLetter("A"));
    }

    @Test
    void camelToUnderscore_ShouldConvertCamelCaseToUnderscore() {
        // 测试正常情况
        assertEquals("user_name", StringExtUtils.camelToUnderscore("userName"));
        assertEquals("user_name_info", StringExtUtils.camelToUnderscore("userNameInfo"));
        assertEquals("abc", StringExtUtils.camelToUnderscore("abc"));
        assertEquals("abc_def_ghi", StringExtUtils.camelToUnderscore("abcDefGhi"));
        
        // 测试特殊情况
        assertEquals("abc_def", StringExtUtils.camelToUnderscore("abcDEF"));
        assertEquals("_abc", StringExtUtils.camelToUnderscore("Abc"));
        
        // 测试边界情况
        assertNull(StringExtUtils.camelToUnderscore(null));
        assertEquals("", StringExtUtils.camelToUnderscore(""));
        assertEquals("a", StringExtUtils.camelToUnderscore("a"));
    }

    @Test
    void underscoreToCamel_ShouldConvertUnderscoreToCamelCase() {
        // 测试正常情况
        assertEquals("userName", StringExtUtils.underscoreToCamel("user_name"));
        assertEquals("userNameInfo", StringExtUtils.underscoreToCamel("user_name_info"));
        assertEquals("abc", StringExtUtils.underscoreToCamel("abc"));
        assertEquals("abcDefGhi", StringExtUtils.underscoreToCamel("abc_def_ghi"));
        
        // 测试特殊情况
        assertEquals("abcDef", StringExtUtils.underscoreToCamel("abc__def"));
        assertEquals("abc", StringExtUtils.underscoreToCamel("abc_"));
        assertEquals("abc", StringExtUtils.underscoreToCamel("_abc"));
        
        // 测试边界情况
        assertNull(StringExtUtils.underscoreToCamel(null));
        assertEquals("", StringExtUtils.underscoreToCamel(""));
        assertEquals("a", StringExtUtils.underscoreToCamel("a"));
    }

    @Test
    void containsChinese_ShouldDetectChineseCharacters() {
        // 测试包含中文的情况
        assertTrue(StringExtUtils.containsChinese("你好"));
        assertTrue(StringExtUtils.containsChinese("Hello你好"));
        assertTrue(StringExtUtils.containsChinese("Hello世界"));
        
        // 测试不包含中文的情况
        assertFalse(StringExtUtils.containsChinese("Hello"));
        assertFalse(StringExtUtils.containsChinese("123"));
        assertFalse(StringExtUtils.containsChinese("!@#$%"));
        
        // 测试特殊字符
        assertFalse(StringExtUtils.containsChinese("ｱｲｳｴｵ")); // 全角字母
        assertFalse(StringExtUtils.containsChinese("あいうえお")); // 日文
        
        // 测试边界情况
        assertFalse(StringExtUtils.containsChinese(null));
        assertFalse(StringExtUtils.containsChinese(""));
        assertFalse(StringExtUtils.containsChinese(" "));
    }

    @Test
    void getByteLength_ShouldReturnCorrectByteLength() {
        // 测试ASCII字符
        assertEquals(5, StringExtUtils.getByteLength("Hello"));
        assertEquals(11, StringExtUtils.getByteLength("Hello World"));
        
        // 测试中文字符（UTF-8编码下，一个中文字符通常占3个字节）
        assertTrue(StringExtUtils.getByteLength("你好") > 2);
        assertTrue(StringExtUtils.getByteLength("Hello你好") > 7);
        
        // 测试特殊字符
        assertEquals(1, StringExtUtils.getByteLength(" "));
        assertEquals(1, StringExtUtils.getByteLength("!"));
        
        // 测试边界情况
        assertEquals(0, StringExtUtils.getByteLength(null));
        assertEquals(0, StringExtUtils.getByteLength(""));
    }
} 