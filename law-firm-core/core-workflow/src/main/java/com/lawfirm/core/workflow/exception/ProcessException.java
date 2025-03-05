package com.lawfirm.core.workflow.exception;

/**
 * 流程异常类
 *
 * @author JunyuZhan
 */
public class ProcessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }
} 
