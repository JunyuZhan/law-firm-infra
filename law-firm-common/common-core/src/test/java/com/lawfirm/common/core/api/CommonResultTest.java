package com.lawfirm.common.core.api;

import com.lawfirm.common.core.constant.ResultCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonResultTest {

    @Test
    void shouldCreateSuccessResult() {
        // given
        String data = "test data";
        
        // when
        CommonResult<String> result = CommonResult.success(data, ResultCode.SUCCESS.getMessage());
        
        // then
        System.out.println("Result: " + result);
        System.out.println("Data: " + result.getData());
        System.out.println("Message: " + result.getMessage());
        System.out.println("Code: " + result.getCode());
        
        assertTrue(result.isSuccess());
        assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        assertEquals(data, result.getData());
        assertEquals(ResultCode.SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void shouldCreateSuccessResultWithMessage() {
        // given
        String message = "custom message";
        
        // when
        CommonResult<Void> result = CommonResult.success(message);
        
        // then
        assertTrue(result.isSuccess());
        assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateErrorResult() {
        // when
        CommonResult<Void> result = CommonResult.error();
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.INTERNAL_ERROR.getCode(), result.getCode());
        assertEquals(ResultCode.INTERNAL_ERROR.getMessage(), result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateErrorResultWithMessage() {
        // given
        String message = "error message";
        
        // when
        CommonResult<Void> result = CommonResult.error(message);
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.INTERNAL_ERROR.getCode(), result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateErrorResultWithResultCode() {
        // when
        CommonResult<Void> result = CommonResult.error(ResultCode.VALIDATION_ERROR);
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.VALIDATION_ERROR.getCode(), result.getCode());
        assertEquals(ResultCode.VALIDATION_ERROR.getMessage(), result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateValidationFailedResult() {
        // given
        String message = "validation failed";
        
        // when
        CommonResult<Void> result = CommonResult.validateFailed(message);
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.VALIDATION_ERROR.getCode(), result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateUnauthorizedResult() {
        // given
        String message = "unauthorized";
        
        // when
        CommonResult<Void> result = CommonResult.unauthorized(message);
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.UNAUTHORIZED.getCode(), result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void shouldCreateForbiddenResult() {
        // given
        String message = "forbidden";
        
        // when
        CommonResult<Void> result = CommonResult.forbidden(message);
        
        // then
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.FORBIDDEN.getCode(), result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }
} 