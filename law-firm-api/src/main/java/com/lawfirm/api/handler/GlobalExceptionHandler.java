package com.lawfirm.api.handler;

import com.lawfirm.api.common.ResponseResult;
import com.lawfirm.common.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ResponseResult<Void> handleBaseException(BaseException e) {
        log.error("基础异常", e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handleValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";
        log.error("参数校验异常: {}", message);
        return ResponseResult.error(400, message);
    }

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数绑定失败";
        log.error("参数绑定异常: {}", message);
        return ResponseResult.error(400, message);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseResult.error("系统异常");
    }
} 