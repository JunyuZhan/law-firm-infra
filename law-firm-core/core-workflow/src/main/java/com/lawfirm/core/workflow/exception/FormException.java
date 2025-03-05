package com.lawfirm.core.workflow.exception;

/**
 * 表单异常类
 * 
 * @author JunyuZhan
 */
public class FormException extends WorkflowException {

    private static final long serialVersionUID = 1L;

    public FormException(String message) {
        super(message);
    }

    public FormException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormException(Throwable cause) {
        super(cause);
    }
} 
