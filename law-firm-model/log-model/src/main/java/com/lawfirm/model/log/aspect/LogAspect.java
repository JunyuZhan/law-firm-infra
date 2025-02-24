package com.lawfirm.model.log.aspect;

import com.lawfirm.model.log.entity.base.BaseLog;

/**
 * 日志切面接口
 */
public interface LogAspect {

    /**
     * 记录操作日志
     *
     * @param joinPoint 切点
     * @return 执行结果
     */
    Object recordOperationLog(Object joinPoint);

    /**
     * 记录审计日志
     *
     * @param joinPoint 切点
     * @return 执行结果
     */
    Object recordAuditLog(Object joinPoint);

    /**
     * 记录系统日志
     *
     * @param joinPoint 切点
     * @return 执行结果
     */
    Object recordSystemLog(Object joinPoint);

    /**
     * 记录异常日志
     *
     * @param joinPoint 切点
     * @param e 异常信息
     */
    void recordErrorLog(Object joinPoint, Throwable e);

    /**
     * 构建日志实体
     *
     * @param joinPoint 切点
     * @return 日志实体
     */
    BaseLog buildLogInfo(Object joinPoint);
} 