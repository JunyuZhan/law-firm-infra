package com.lawfirm.api.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lawfirm.api.util.ApiDocPathHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.lawfirm.api.VbenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

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
    public ResponseEntity<?> handleGenericException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        // 如果是API文档相关路径，记录详细错误日志，然后返回空对象避免前端解析错误
        if (apiDocPathHelper.isApiDocPath(path)) {
            // 记录详细错误日志，包括堆栈跟踪
            log.error("处理 API 文档路径 '{}' 时捕获到异常:", path, ex);
            
            // 对于文档页面，仍然返回一个空的JSON对象，但状态码可以考虑改为 500
            // return ResponseEntity.ok().build(); // Keeps 200 OK, empty body
            // Or return an error response:
             Map<String, Object> errorBody = new HashMap<>();
             errorBody.put("error", "API Documentation Generation Failed");
             errorBody.put("message", ex.getMessage()); // Include exception message
             errorBody.put("path", path);
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .contentType(MediaType.APPLICATION_JSON) // Ensure content type
                                  .body(errorBody); // Return error details
        }
        
        // --- 处理非 API 文档路径的通用异常 --- 
        log.error("请求路径 '{}' 发生未处理异常:", path, ex); // Log non-API doc errors too
        
        Map<String, Object> data = new HashMap<>();
        data.put("path", path);
        data.put("message", "服务器内部错误: " + ex.getMessage()); // More generic message for users
        
        // It's generally better to return a structured error response like VbenResult here too
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(VbenResult.error(500, "服务器内部错误"));
        // Sticking to original logic for now, but consider standardizing error responses
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(VbenResult.error("服务器内部错误: " + ex.getMessage())); 
    }
}