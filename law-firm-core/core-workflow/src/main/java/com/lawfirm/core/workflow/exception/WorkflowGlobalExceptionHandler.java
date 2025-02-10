package com.lawfirm.core.workflow.exception;

import com.lawfirm.common.core.exception.GlobalExceptionHandler;
import com.lawfirm.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class WorkflowGlobalExceptionHandler extends GlobalExceptionHandler {
    
    /**
     * 处理Flowable异常
     */
    @ExceptionHandler(FlowableException.class)
    public Result<Void> handleFlowableException(FlowableException e) {
        log.error("工作流引擎异常", e);
        return Result.error(500, e.getMessage());
    }
}

/**
 * 业务异常
 */
class BusinessException extends RuntimeException {
    private final String code;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}

/**
 * 错误响应
 */
class ErrorResponse {
    private final String code;
    private final String message;
    
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
} 