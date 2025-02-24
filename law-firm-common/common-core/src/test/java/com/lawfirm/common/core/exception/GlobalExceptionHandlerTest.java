package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleUnknownException() {
        // given
        RuntimeException exception = new RuntimeException("Unknown error");

        // when
        CommonResult<Void> result = handler.handleThrowable(exception);

        // then
        assertEquals(ResultCode.INTERNAL_ERROR.getCode(), result.getCode());
        assertEquals(ResultCode.INTERNAL_ERROR.getMessage(), result.getMessage());
    }

    @Test
    void shouldHandleValidationException() {
        // given
        ValidationException exception = new ValidationException("Invalid parameter");

        // when
        CommonResult<Void> result = handler.handleValidationException(exception);

        // then
        assertEquals(ResultCode.VALIDATION_ERROR.getCode(), result.getCode());
        assertEquals(ResultCode.VALIDATION_ERROR.getMessage(), result.getMessage());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        // when
        CommonResult<Void> result = handler.handleValidationException(exception);

        // then
        assertEquals(ResultCode.VALIDATION_ERROR.getCode(), result.getCode());
    }

    @Test
    void shouldHandleBindException() {
        // given
        BindException exception = mock(BindException.class);

        // when
        CommonResult<Void> result = handler.handleValidationException(exception);

        // then
        assertEquals(ResultCode.VALIDATION_ERROR.getCode(), result.getCode());
    }

    @Test
    void shouldHandleHttpRequestMethodNotSupportedException() {
        // given
        HttpRequestMethodNotSupportedException exception = 
            new HttpRequestMethodNotSupportedException("POST", Collections.singletonList("GET"));

        // when
        CommonResult<Void> result = handler.handleHttpRequestMethodNotSupportedException(exception);

        // then
        assertEquals(ResultCode.METHOD_NOT_ALLOWED.getCode(), result.getCode());
    }

    @Test
    void shouldHandleMaxUploadSizeExceededException() {
        // given
        MaxUploadSizeExceededException exception = new MaxUploadSizeExceededException(1000L);

        // when
        CommonResult<Void> result = handler.handleMaxUploadSizeExceededException(exception);

        // then
        assertEquals(ResultCode.BAD_REQUEST.getCode(), result.getCode());
        assertTrue(result.getMessage().contains("上传文件大小超出限制"));
    }
} 