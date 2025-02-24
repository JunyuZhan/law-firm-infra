package com.lawfirm.model.base.support.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@DisplayName("验证器集合测试")
class ValidatorsTest {

    @Test
    @DisplayName("非空验证器 - 非空值")
    void notNull_ShouldValidateNonNullValue() {
        assertTrue(Validators.notNull().test("test"));
    }

    @Test
    @DisplayName("非空验证器 - 空值")
    void notNull_ShouldInvalidateNullValue() {
        assertFalse(Validators.notNull().test(null));
    }

    @Test
    @DisplayName("字符串非空验证器 - 有效字符串")
    void notBlank_ShouldValidateNonBlankString() {
        assertTrue(Validators.notBlank().test("test"));
        assertTrue(Validators.notBlank().test("  test  "));
    }

    @Test
    @DisplayName("字符串非空验证器 - 空白字符串")
    void notBlank_ShouldInvalidateBlankString() {
        assertFalse(Validators.notBlank().test(""));
        assertFalse(Validators.notBlank().test(" "));
        assertFalse(Validators.notBlank().test(null));
    }

    @Test
    @DisplayName("集合非空验证器 - 非空集合")
    void notEmpty_ShouldValidateNonEmptyCollection() {
        assertTrue(Validators.notEmpty().test(Arrays.asList("test")));
        assertTrue(Validators.notEmpty().test(Collections.singleton("test")));
    }

    @Test
    @DisplayName("集合非空验证器 - 空集合")
    void notEmpty_ShouldInvalidateEmptyCollection() {
        assertFalse(Validators.notEmpty().test(Collections.emptyList()));
        assertFalse(Validators.notEmpty().test(null));
    }

    @Test
    @DisplayName("Map非空验证器 - 非空Map")
    void mapNotEmpty_ShouldValidateNonEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertTrue(Validators.<String, String>mapNotEmpty().test(map));
    }

    @Test
    @DisplayName("Map非空验证器 - 空Map")
    void mapNotEmpty_ShouldInvalidateEmptyMap() {
        assertFalse(Validators.<String, String>mapNotEmpty().test(Collections.emptyMap()));
        assertFalse(Validators.<String, String>mapNotEmpty().test(null));
    }

    @Test
    @DisplayName("正则验证器 - 匹配字符串")
    void regex_ShouldValidateMatchingString() {
        assertTrue(Validators.regex("^\\d{4}$").test("1234"));
    }

    @Test
    @DisplayName("正则验证器 - 不匹配字符串")
    void regex_ShouldInvalidateNonMatchingString() {
        assertFalse(Validators.regex("^\\d{4}$").test("123"));
        assertFalse(Validators.regex("^\\d{4}$").test("12345"));
        assertFalse(Validators.regex("^\\d{4}$").test("abcd"));
        assertFalse(Validators.regex("^\\d{4}$").test(null));
    }

    @Test
    @DisplayName("范围验证器 - 范围内的值")
    void range_ShouldValidateNumberInRange() {
        assertTrue(Validators.range(1, 10).test(5));
        assertTrue(Validators.range(1, 10).test(1));
        assertTrue(Validators.range(1, 10).test(10));
    }

    @Test
    @DisplayName("范围验证器 - 范围外的值")
    void range_ShouldInvalidateNumberOutOfRange() {
        assertFalse(Validators.range(1, 10).test(0));
        assertFalse(Validators.range(1, 10).test(11));
        assertFalse(Validators.range(1, 10).test(null));
    }

    @Test
    @DisplayName("长度验证器 - 有效长度")
    void length_ShouldValidateStringWithinLength() {
        assertTrue(Validators.length(2, 5).test("abc"));
        assertTrue(Validators.length(2, 5).test("ab"));
        assertTrue(Validators.length(2, 5).test("abcde"));
    }

    @Test
    @DisplayName("长度验证器 - 无效长度")
    void length_ShouldInvalidateStringOutsideLength() {
        assertFalse(Validators.length(2, 5).test("a"));
        assertFalse(Validators.length(2, 5).test("abcdef"));
        assertFalse(Validators.length(2, 5).test(""));
        assertFalse(Validators.length(2, 5).test(null));
    }
} 