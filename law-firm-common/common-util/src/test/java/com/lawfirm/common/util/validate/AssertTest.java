package com.lawfirm.common.util.validate;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.ValidationException;
import com.lawfirm.common.util.BaseUtilTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AssertTest extends BaseUtilTest {

    @Test
    void notNull_ShouldHandleNestedObjects() {
        // 测试嵌套对象
        Map<String, List<String>> nestedObj = new HashMap<>();
        nestedObj.put("test", Arrays.asList("1", "2"));
        assertDoesNotThrow(() -> Assert.notNull(nestedObj, ResultCode.VALIDATION_ERROR));
        
        // 测试嵌套空对象
        nestedObj.put("empty", null);
        assertDoesNotThrow(() -> Assert.notNull(nestedObj, ResultCode.VALIDATION_ERROR));
        
        // 测试null
        assertThrows(ValidationException.class, () -> 
            Assert.notNull(null, ResultCode.VALIDATION_ERROR));
    }

    @Test
    void notEmpty_ShouldHandleComplexCollections() {
        // 测试复杂集合
        List<Map<String, Object>> complexList = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("id", 1);
        item.put("name", "test");
        complexList.add(item);
        
        assertDoesNotThrow(() -> Assert.notEmpty(complexList, ResultCode.VALIDATION_ERROR));
        
        // 测试空元素的复杂集合
        complexList.add(null);
        assertDoesNotThrow(() -> Assert.notEmpty(complexList, ResultCode.VALIDATION_ERROR));
        
        // 测试空集合
        assertThrows(ValidationException.class, () -> 
            Assert.notEmpty(new ArrayList<>(), ResultCode.VALIDATION_ERROR));
        
        // 测试null集合
        assertThrows(ValidationException.class, () -> 
            Assert.notEmpty((Collection<?>) null, ResultCode.VALIDATION_ERROR));
    }

    @Test
    void notBlank_ShouldHandleSpecialCharacters() {
        // 测试特殊字符
        assertDoesNotThrow(() -> Assert.notBlank("测试@#¥%", ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.notBlank("Hello世界", ResultCode.VALIDATION_ERROR));
        
        // 测试空白字符
        assertThrows(ValidationException.class, () -> 
            Assert.notBlank("\t\n\r", ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.notBlank("   ", ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.notBlank("", ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.notBlank(null, ResultCode.VALIDATION_ERROR));
    }

    @Test
    void lengthInRange_ShouldHandleBoundaryValues() {
        String str = "测试字符串";
        // 测试边界值
        assertDoesNotThrow(() -> Assert.lengthInRange(str, 5, 5, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.lengthInRange(str, 0, 10, ResultCode.VALIDATION_ERROR));
        
        // 测试边界外的值
        assertThrows(ValidationException.class, () -> 
            Assert.lengthInRange(str, 10, 20, ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.lengthInRange(str, -1, 5, ResultCode.VALIDATION_ERROR));
        
        // 测试特殊情况
        assertThrows(ValidationException.class, () -> 
            Assert.lengthInRange(null, 0, 10, ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.lengthInRange(str, 5, 3, ResultCode.VALIDATION_ERROR)); // min > max
    }

    @Test
    void equals_ShouldHandleComplexEquality() {
        // 测试不同类型的相等性
        assertDoesNotThrow(() -> Assert.equals(1, 1, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.equals(null, null, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.equals("test", "test", ResultCode.VALIDATION_ERROR));
        
        // 测试不相等的情况
        assertThrows(ValidationException.class, () -> 
            Assert.equals(1, "1", ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.equals(null, "null", ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.equals(1.0, 1, ResultCode.VALIDATION_ERROR));
    }

    @Test
    void inRange_ShouldHandleBoundaryValues() {
        // 测试边界值
        assertDoesNotThrow(() -> Assert.inRange(0, 0, 10, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.inRange(10, 0, 10, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.inRange(5, 0, 10, ResultCode.VALIDATION_ERROR));
        
        // 测试边界外的值
        assertThrows(ValidationException.class, () -> 
            Assert.inRange(-1, 0, 10, ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.inRange(11, 0, 10, ResultCode.VALIDATION_ERROR));
        
        // 测试特殊情况
        assertThrows(ValidationException.class, () -> 
            Assert.inRange(5, 10, 0, ResultCode.VALIDATION_ERROR)); // min > max
    }

    @Test
    void isInstanceOf_ShouldHandleInheritance() {
        // 测试继承关系
        ArrayList<String> list = new ArrayList<>();
        assertDoesNotThrow(() -> Assert.isInstanceOf(List.class, list, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.isInstanceOf(Collection.class, list, ResultCode.VALIDATION_ERROR));
        assertDoesNotThrow(() -> Assert.isInstanceOf(Object.class, list, ResultCode.VALIDATION_ERROR));
        
        // 测试不匹配的类型
        assertThrows(ValidationException.class, () -> 
            Assert.isInstanceOf(Map.class, list, ResultCode.VALIDATION_ERROR));
        
        // 测试null值
        assertThrows(ValidationException.class, () -> 
            Assert.isInstanceOf(List.class, null, ResultCode.VALIDATION_ERROR));
        assertThrows(ValidationException.class, () -> 
            Assert.isInstanceOf(null, list, ResultCode.VALIDATION_ERROR));
    }
} 