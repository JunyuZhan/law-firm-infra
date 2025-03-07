package com.lawfirm.auth.exception;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.security.crypto.SensitiveDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 认证授权异常处理器
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    private final SensitiveDataService sensitiveDataService;
    
    // 定义敏感字段数组
    private static final String[] SENSITIVE_FIELDS = {"password", "mobile", "phone", "email", "idCard"};

    /**
     * 处理认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public CommonResult<?> handleAuthenticationException(AuthenticationException e) {
        String sanitizedMessage = sanitizeExceptionMessage(e.getMessage());
        log.error("认证异常: {}", sanitizedMessage);
        
        if (e instanceof BadCredentialsException) {
            return CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        } else if (e instanceof DisabledException) {
            return CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), "账号已被禁用");
        } else if (e instanceof LockedException) {
            return CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), "账号已被锁定");
        } else {
            return CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), "认证失败：" + sanitizedMessage);
        }
    }
    
    /**
     * 处理授权异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error("授权异常: {}", e.getMessage(), e);
        return CommonResult.error(ResultCode.FORBIDDEN.getCode(), "权限不足");
    }
    
    /**
     * 处理JWT令牌异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public CommonResult<?> handleInvalidTokenException(InvalidTokenException e) {
        log.error("令牌异常: {}", e.getMessage(), e);
        return CommonResult.error(ResultCode.UNAUTHORIZED.getCode(), e.getMessage());
    }
    
    /**
     * 处理参数验证异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public CommonResult<?> handleValidationException(Exception e) {
        BindingResult bindingResult;
        
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            bindingResult = ((BindException) e).getBindingResult();
        }
        
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
                
        log.error("参数验证异常: {}", errorMessage);
        return CommonResult.validateFailed(errorMessage);
    }
    
    /**
     * 处理业务异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public CommonResult<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        // 由于BusinessException可能没有getCode方法，使用通用的错误码
        int code = ResultCode.BAD_REQUEST.getCode();
        try {
            // 尝试获取异常中的错误码，如果不存在则使用默认值
            if (e instanceof HasErrorCode) {
                code = ((HasErrorCode) e).getCode();
            }
        } catch (Exception ex) {
            // 忽略可能的异常
        }
        return CommonResult.error(code, e.getMessage());
    }
    
    /**
     * 处理其他异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception e) {
        log.error("系统异常", e);
        return CommonResult.error("服务器内部错误，请联系管理员");
    }
    
    /**
     * 脱敏异常信息中的敏感数据
     */
    private String sanitizeExceptionMessage(String message) {
        if (message == null) {
            return "";
        }
        
        String sanitized = message;
        
        // 掩盖手机号
        Pattern phonePattern = Pattern.compile("\\b1[3-9]\\d{9}\\b");
        Matcher phoneMatcher = phonePattern.matcher(sanitized);
        while (phoneMatcher.find()) {
            String phone = phoneMatcher.group();
            String maskedPhone = sensitiveDataService.maskPhoneNumber(phone);
            sanitized = sanitized.replace(phone, maskedPhone);
        }
        
        // 掩盖邮箱
        Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
        Matcher emailMatcher = emailPattern.matcher(sanitized);
        while (emailMatcher.find()) {
            String email = emailMatcher.group();
            String maskedEmail = sensitiveDataService.maskEmail(email);
            sanitized = sanitized.replace(email, maskedEmail);
        }
        
        return sanitized;
    }
    
    /**
     * 具有错误码的异常接口
     */
    private interface HasErrorCode {
        int getCode();
    }
}
