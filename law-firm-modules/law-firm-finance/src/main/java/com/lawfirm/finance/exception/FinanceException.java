package com.lawfirm.finance.exception;

/**
 * 财务模块业务异常
 */
public class FinanceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public FinanceException(String message) {
        super(message);
    }
    public FinanceException(String message, Throwable cause) {
        super(message, cause);
    }
    public static FinanceException failed(String action, Throwable cause) {
        return new FinanceException(action + "失败: " + cause.getMessage(), cause);
    }
    public static FinanceException notFound(String entity) {
        return new FinanceException(entity + "不存在");
    }
} 