package com.lawfirm.document.exception;

/**
 * 文档模块业务异常
 */
public class DocumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DocumentException(String message) {
        super(message);
    }
    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }
    public static DocumentException noPermission(String action) {
        return new DocumentException("无权限" + action);
    }
    public static DocumentException failed(String action, Throwable cause) {
        return new DocumentException(action + "失败: " + cause.getMessage(), cause);
    }
    public static DocumentException notFound(String entity) {
        return new DocumentException(entity + "不存在");
    }
} 