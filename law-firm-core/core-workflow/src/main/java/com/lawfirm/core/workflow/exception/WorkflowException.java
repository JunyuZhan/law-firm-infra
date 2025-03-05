package com.lawfirm.core.workflow.exception;

/**
 * 工作流异常基类
 * 
 * @author JunyuZhan
 */
public class WorkflowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }
} 
