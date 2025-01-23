package com.lawfirm.core.workflow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 工作流自定义异常
 */
@Getter
public class WorkflowException extends RuntimeException {

    private final int status;
    
    public WorkflowException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST.value();
    }
    
    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST.value();
    }
    
    public WorkflowException(String message, int status) {
        super(message);
        this.status = status;
    }
    
    public WorkflowException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }
    
    /**
     * 创建一个BAD_REQUEST异常
     */
    public static WorkflowException badRequest(String message) {
        return new WorkflowException(message, HttpStatus.BAD_REQUEST.value());
    }
    
    /**
     * 创建一个NOT_FOUND异常
     */
    public static WorkflowException notFound(String message) {
        return new WorkflowException(message, HttpStatus.NOT_FOUND.value());
    }
    
    /**
     * 创建一个FORBIDDEN异常
     */
    public static WorkflowException forbidden(String message) {
        return new WorkflowException(message, HttpStatus.FORBIDDEN.value());
    }
    
    /**
     * 创建一个INTERNAL_SERVER_ERROR异常
     */
    public static WorkflowException internalServerError(String message) {
        return new WorkflowException(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    
    /**
     * 创建一个INTERNAL_SERVER_ERROR异常(带cause)
     */
    public static WorkflowException internalServerError(String message, Throwable cause) {
        return new WorkflowException(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
} 