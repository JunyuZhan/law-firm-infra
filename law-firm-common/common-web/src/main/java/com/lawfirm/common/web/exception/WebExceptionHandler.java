package com.lawfirm.common.web.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Web层异常处理器
 * 处理Web层面的框架异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lawfirm.common.web")
public class WebExceptionHandler {

    /**
     * 处理框架异常
     */
    @ExceptionHandler(FrameworkException.class)
    public CommonResult<?> handleFrameworkException(FrameworkException e) {
        log.error("框架异常", e);
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return CommonResult.error(ResultCode.VALIDATION_ERROR.getCode(), fieldError.getDefaultMessage());
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数绑定异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return CommonResult.error(ResultCode.VALIDATION_ERROR.getCode(), fieldError.getDefaultMessage());
    }

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception e) {
        log.error("系统异常:", e);
        return CommonResult.error(ResultCode.INTERNAL_ERROR);
    }
} 