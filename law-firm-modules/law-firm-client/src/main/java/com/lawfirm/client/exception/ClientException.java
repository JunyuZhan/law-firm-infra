package com.lawfirm.client.exception;

public class ClientException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ClientException(String message) {
        super(message);
    }
    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
    public static ClientException notFound(Long clientId) {
        return new ClientException("客户不存在，ID=" + clientId);
    }
    public static ClientException invalidType(String typeStr) {
        return new ClientException("无效的客户类型：" + typeStr);
    }
    // 可根据需要扩展更多静态工厂方法
} 