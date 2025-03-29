package com.lawfirm.api.handler;

import com.lawfirm.api.VbenResult;
import com.lawfirm.common.core.exception.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * API模块异常处理器
 * <p>专门处理API模块特有的异常，返回VbenResult格式响应</p>
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lawfirm.api")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

    /**
     * 处理基础异常
     */
    @ExceptionHandler(BaseException.class)
    public VbenResult<Void> handleBaseException(BaseException e) {
        log.error("API模块基础异常", e);
        return VbenResult.error(e.getCode(), e.getMessage());
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