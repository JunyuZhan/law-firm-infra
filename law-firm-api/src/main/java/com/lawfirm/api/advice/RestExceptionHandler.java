package com.lawfirm.api.advice;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.exception.FrameworkException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST API全局异常处理
 * 处理所有API请求中的异常，并返回标准格式的错误响应
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResult<Void>> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.error(e.getCode(), e.getMessage()));
    }

    /**
     * 处理框架异常
     */
    @ExceptionHandler(FrameworkException.class)
    public ResponseEntity<CommonResult<Void>> handleFrameworkException(FrameworkException e) {
        log.error("框架异常: code={}, message={}", e.getCode(), e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResult.error(e.getCode(), e.getMessage()));
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
        HttpMessageNotReadableException.class,
        ConstraintViolationException.class
    })
    public ResponseEntity<CommonResult<Void>> handleValidationException(Exception e) {
        String message = "参数验证失败";
        
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                message = fieldErrors.stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("; "));
            }
        } else if (e instanceof BindException) {
            BindException be = (BindException) e;
            List<FieldError> fieldErrors = be.getBindingResult().getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                message = fieldErrors.stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("; "));
            }
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
            if (!violations.isEmpty()) {
                message = violations.stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect(Collectors.joining("; "));
            }
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrpe = (MissingServletRequestParameterException) e;
            message = "缺少必要参数: " + msrpe.getParameterName();
        } else if (e instanceof HttpMessageNotReadableException) {
            message = "请求体无法解析";
        } else {
            message = e.getMessage();
        }

        log.warn("参数验证异常: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.error(ResultCode.VALIDATION_ERROR.getCode(), message));
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<CommonResult<Void>> handleAuthenticationException(Exception e) {
        log.warn("认证异常: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), "认证失败: " + e.getMessage()));
    }

    /**
     * 处理授权异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResult<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("权限不足: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(CommonResult.error(ResultCode.FORBIDDEN.getCode(), "权限不足，无法访问"));
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResult<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("请求方法不支持: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(CommonResult.error(ResultCode.METHOD_NOT_ALLOWED.getCode(), e.getMessage()));
    }

    /**
     * 处理文件上传超出限制异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<CommonResult<Void>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("上传文件大小超出限制: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.error(ResultCode.BAD_REQUEST.getCode(), "上传文件大小超出限制"));
    }

    /**
     * 处理数据库相关异常
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<CommonResult<Void>> handleSQLException(Exception e) {
        log.error("数据库操作异常", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResult.error(ResultCode.INTERNAL_ERROR.getCode(), "数据库操作异常"));
    }

    /**
     * 处理IO异常
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<CommonResult<Void>> handleIOException(IOException e) {
        log.error("IO异常", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResult.error(ResultCode.INTERNAL_ERROR.getCode(), "IO操作异常"));
    }

    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult<Void>> handleException(Exception e) {
        log.error("未预期的异常", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResult.error(ResultCode.INTERNAL_ERROR.getCode(), "服务器内部错误"));
    }
}