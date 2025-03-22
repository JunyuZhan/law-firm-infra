package com.lawfirm.common.web.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.exception.FrameworkException;
import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Web层异常处理器
 * 处理Web层面的框架异常
 * 继承GlobalExceptionHandler以重用通用异常处理逻辑
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lawfirm.common.web")
public class WebExceptionHandler extends GlobalExceptionHandler {

    /**
     * 处理框架异常
     */
    @ExceptionHandler(FrameworkException.class)
    public CommonResult<?> handleFrameworkException(FrameworkException e) {
        log.error("Web层框架异常", e);
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理Web模块特定异常
     * 可在此处添加Web模块特有的异常处理逻辑
     */
} 