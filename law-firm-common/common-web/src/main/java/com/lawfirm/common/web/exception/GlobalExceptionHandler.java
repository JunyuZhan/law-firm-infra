package com.lawfirm.common.web.exception;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.web.response.R;
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

    @ExceptionHandler(BusinessException.class)
    public R<?> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return R.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public R<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数绑定异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return R.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error("系统异常:", e);
        return R.error();
    }
} 