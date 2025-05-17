package com.lawfirm.knowledge.exception;

/**
 * 知识库模块业务异常
 */
public class KnowledgeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public KnowledgeException(String message) {
        super(message);
    }
    public KnowledgeException(String message, Throwable cause) {
        super(message, cause);
    }
    public static KnowledgeException failed(String action, Throwable cause) {
        return new KnowledgeException(action + "失败: " + cause.getMessage(), cause);
    }
    public static KnowledgeException notFound(String entity) {
        return new KnowledgeException(entity + "不存在");
    }
} 