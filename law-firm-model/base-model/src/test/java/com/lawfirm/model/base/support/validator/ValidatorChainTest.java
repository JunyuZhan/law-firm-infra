package com.lawfirm.model.base.support.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("验证链测试")
class ValidatorChainTest {

    @Test
    @DisplayName("当所有验证器都通过时，应返回true")
    void validate_ShouldReturnTrueForValidChain() {
        ValidatorChain<String> chain = new ValidatorChain<>();
        chain.addValidator(str -> true)
             .addValidator(str -> true);
        
        assertTrue(chain.validate("test"));
    }

    @Test
    @DisplayName("当任一验证器失败时，应返回false")
    void validate_ShouldReturnFalseIfAnyValidatorFails() {
        ValidatorChain<String> chain = new ValidatorChain<>();
        chain.addValidator(str -> true)
             .addValidator(str -> false);
        
        assertFalse(chain.validate("test"));
    }

    @Test
    @DisplayName("空验证链应返回true")
    void validate_ShouldHandleEmptyChain() {
        ValidatorChain<String> chain = new ValidatorChain<>();
        assertTrue(chain.validate("test"));
    }

    @Test
    @DisplayName("应正确处理null输入")
    void validate_ShouldHandleNullInput() {
        ValidatorChain<String> chain = new ValidatorChain<>();
        chain.addValidator(str -> str != null);
        
        assertFalse(chain.validate(null));
    }

    @Test
    @DisplayName("添加null验证器时应抛出异常")
    void addValidator_ShouldHandleNullValidator() {
        ValidatorChain<String> chain = new ValidatorChain<>();
        assertThrows(IllegalArgumentException.class, () -> chain.addValidator(null));
    }
} 