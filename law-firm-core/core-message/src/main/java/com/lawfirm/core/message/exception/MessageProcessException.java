package com.lawfirm.core.message.exception;

public class MessageProcessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MessageProcessException(String message) {
        super(message);
    }

    public MessageProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageProcessException(Throwable cause) {
        super(cause);
    }
} 