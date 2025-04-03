package com.lawfirm.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API全局异常处理
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "服务器内部错误");
        response.put("code", 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Map<String, Object>> handleValidationException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "参数验证失败");
        response.put("code", 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}