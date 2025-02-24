package com.lawfirm.model.log.aspect;

/**
 * 日志切点定义接口
 */
public interface LogPointcut {

    /**
     * 操作日志切点
     */
    String OPERATION_LOG = "@annotation(com.lawfirm.model.log.annotation.OperationLog)";

    /**
     * 审计日志切点
     */
    String AUDIT_LOG = "@annotation(com.lawfirm.model.log.annotation.AuditLog)";

    /**
     * 系统日志切点
     */
    String SYSTEM_LOG = "@annotation(com.lawfirm.model.log.annotation.SystemLog)";

    /**
     * 异常日志切点
     */
    String ERROR_LOG = "execution(* com.lawfirm..*.*(..))";
} 