package com.lawfirm.api.handler;

import com.lawfirm.api.VbenResult;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BaseException;
import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * API模块异常处理器
 * 继承自通用层的异常处理逻辑，并适配API模块特定需求
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lawfirm.api")
public class ApiExceptionHandler extends GlobalExceptionHandler {

    /**
     * 处理基础异常
     */
    @ExceptionHandler(BaseException.class)
    public VbenResult<Void> handleBaseException(BaseException e) {
        log.error("API模块基础异常", e);
        return VbenResult.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public VbenResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败: {}", message);
        return VbenResult.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public VbenResult<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("参数绑定失败: {}", message);
        return VbenResult.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public VbenResult<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        log.warn("约束校验失败: {}", message);
        return VbenResult.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public VbenResult<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = "参数'" + e.getName() + "'的类型应为" + 
                Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        log.warn("参数类型不匹配: {}", message);
        return VbenResult.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    /**
     * 处理缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public VbenResult<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = "缺少必要参数: " + e.getParameterName();
        log.warn(message);
        return VbenResult.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    /**
     * 处理其他未预期的异常
     */
    @ExceptionHandler(Exception.class)
    public VbenResult<Void> handleException(Exception e) {
        log.error("未预期的API异常", e);
        return VbenResult.serverError("服务器内部错误: " + e.getMessage());
    }
} 