package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器
 * 只处理框架级的基础异常，业务异常应该由具体的业务模块自行处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult<Void> handleThrowable(Throwable e) {
        log.error("未知异常", e);
        return CommonResult.error(ResultCode.INTERNAL_ERROR);
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        BindException.class,
        ValidationException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class
    })
    public CommonResult<Void> handleValidationException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            if (manve.getBindingResult() != null && !manve.getBindingResult().getFieldErrors().isEmpty()) {
                log.warn("无效参数: {}", manve.getBindingResult().getFieldErrors());
            } else {
                log.warn("无效参数: BindingResult 为 null 或没有错误");
            }
        }
        log.warn("参数验证异常: {}", e.getMessage());
        return CommonResult.error(ResultCode.VALIDATION_ERROR);
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("请求方法不支持: {}", e.getMessage());
        return CommonResult.error(ResultCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 处理文件上传超出限制异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResult<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("上传文件大小超出限制: {}", e.getMessage());
        return CommonResult.error(ResultCode.BAD_REQUEST.getCode(), "上传文件大小超出限制");
    }
} 