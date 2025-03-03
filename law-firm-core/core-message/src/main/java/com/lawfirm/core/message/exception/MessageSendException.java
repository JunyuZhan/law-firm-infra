package com.lawfirm.core.message.exception;

public class MessageSendException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MessageSendException(String message) {
        super(message);
    }

    public MessageSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSendException(Throwable cause) {
        super(cause);
    }
} 