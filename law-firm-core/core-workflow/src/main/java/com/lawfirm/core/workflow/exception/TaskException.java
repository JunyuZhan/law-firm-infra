package com.lawfirm.core.workflow.exception;

/**
 * 任务异常类
 * 
 * @author JunyuZhan
 */
public class TaskException extends WorkflowException {

    private static final long serialVersionUID = 1L;

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
} 
