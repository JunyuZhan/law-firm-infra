package com.lawfirm.core.workflow.exception;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作流异常处理器
 */
@Slf4j
@RestControllerAdvice
public class WorkflowExceptionHandler {

    private final MongoTemplate mongoTemplate;
    
    public WorkflowExceptionHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    /**
     * 处理Flowable对象未找到异常
     */
    @ExceptionHandler(FlowableObjectNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFlowableObjectNotFoundException(FlowableObjectNotFoundException e) {
        String message = "未找到指定的工作流对象: " + e.getMessage();
        log.warn(message, e);
        
        Map<String, Object> errorInfo = createErrorInfo(e, message, HttpStatus.NOT_FOUND.value());
        saveErrorInfo(errorInfo);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }
    
    /**
     * 处理Flowable业务异常
     */
    @ExceptionHandler(FlowableException.class)
    public ResponseEntity<Map<String, Object>> handleFlowableException(FlowableException e) {
        String message = "工作流操作失败: " + e.getMessage();
        log.error(message, e);
        
        Map<String, Object> errorInfo = createErrorInfo(e, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        saveErrorInfo(errorInfo);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
    }
    
    /**
     * 处理工作流自定义异常
     */
    @ExceptionHandler(WorkflowException.class)
    public ResponseEntity<Map<String, Object>> handleWorkflowException(WorkflowException e) {
        log.error("工作流业务异常", e);
        
        Map<String, Object> errorInfo = createErrorInfo(e, e.getMessage(), e.getStatus());
        saveErrorInfo(errorInfo);
        
        return ResponseEntity.status(e.getStatus()).body(errorInfo);
    }
    
    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        String message = "系统内部错误: " + e.getMessage();
        log.error(message, e);
        
        Map<String, Object> errorInfo = createErrorInfo(e, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        saveErrorInfo(errorInfo);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
    }
    
    /**
     * 创建错误信息
     */
    private Map<String, Object> createErrorInfo(Exception e, String message, int status) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", LocalDateTime.now());
        errorInfo.put("status", status);
        errorInfo.put("error", e.getClass().getSimpleName());
        errorInfo.put("message", message);
        errorInfo.put("path", ""); // TODO: 获取请求路径
        return errorInfo;
    }
    
    /**
     * 保存错误信息
     */
    private void saveErrorInfo(Map<String, Object> errorInfo) {
        try {
            mongoTemplate.save(errorInfo, "workflow_errors");
        } catch (Exception e) {
            log.error("Failed to save error info", e);
        }
    }
} 