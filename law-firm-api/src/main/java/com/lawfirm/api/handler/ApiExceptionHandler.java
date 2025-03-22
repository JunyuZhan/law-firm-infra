package com.lawfirm.api.handler;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.exception.BaseException;
import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public CommonResult<Void> handleBaseException(BaseException e) {
        log.error("API模块基础异常", e);
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理API模块特定异常
     * 可在此处添加API模块特有的异常处理逻辑
     */
} 