package com.lawfirm.api.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lawfirm.api.util.ApiDocPathHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.lawfirm.api.VbenResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局REST接口异常处理器
 * <p>
 * 处理Controller层抛出的异常，确保返回友好的错误信息
 * </p>
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    private ApiDocPathHelper apiDocPathHelper;

    /**
     * 处理JSON解析错误
     */
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public VbenResult<Map<String, Object>> handleJsonProcessingException(JsonProcessingException ex, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("path", request.getRequestURI());
        data.put("message", "JSON解析错误: " + ex.getMessage());
        
        return VbenResult.error("JSON解析错误: " + ex.getMessage());
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<VbenResult<Map<String, Object>>> handleGenericException(Exception ex, HttpServletRequest request) {
        // 如果是API文档相关路径，返回空对象避免前端解析错误
        String path = request.getRequestURI();
        if (apiDocPathHelper.isApiDocPath(path)) {
            // 对于文档页面，返回一个空的JSON对象
            return ResponseEntity.ok().build();
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("path", path);
        data.put("message", ex.getMessage());
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(VbenResult.error(ex.getMessage()));
    }
}