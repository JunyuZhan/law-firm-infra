package com.lawfirm.model.log.spi;

/**
 * 日志切点定义接口
 * 定义各种日志类型的切点表达式
 */
public interface LogPointcut {

    /**
     * 操作日志切点
     */
    String OPERATION_LOG = "@annotation(com.lawfirm.common.log.annotation.OperationLog)";

    /**
     * 审计日志切点
     */
    String AUDIT_LOG = "@annotation(com.lawfirm.common.log.annotation.AuditLog)";

    /**
     * 系统日志切点
     */
    String SYSTEM_LOG = "@annotation(com.lawfirm.common.log.annotation.SystemLog)";

    /**
     * 异常日志切点
     */
    String ERROR_LOG = "execution(* com.lawfirm..*.*(..))";
} 