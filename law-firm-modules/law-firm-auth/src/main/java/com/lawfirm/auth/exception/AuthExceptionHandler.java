package com.lawfirm.auth.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证模块全局异常处理器
 * 继承GlobalExceptionHandler，只处理认证模块特有的异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lawfirm.auth")
public class AuthExceptionHandler extends GlobalExceptionHandler {
    
    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult<?> handleAuthException(AuthException e) {
        log.error("认证异常: {}", e.getMessage());
        return CommonResult.error(401, e.getMessage());
    }
    
    /**
     * 处理Spring Security认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult<?> handleAuthenticationException(AuthenticationException e) {
        log.error("认证异常: {}", e.getMessage());
        if (e instanceof BadCredentialsException) {
            return CommonResult.error(401, "用户名或密码错误");
        }
        return CommonResult.error(401, "认证失败");
    }
    
    /**
     * 处理Spring Security授权异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error("授权异常: {}", e.getMessage());
        return CommonResult.error(403, "没有权限访问该资源");
    }
} 