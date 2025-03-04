package com.lawfirm.model.log.service;

import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;

/**
 * 审计服务接口
 */
public interface AuditService {

    /**
     * 记录审计日志
     *
     * @param auditLog 审计日志DTO
     */
    void log(AuditLogDTO auditLog);

    /**
     * 记录审计记录
     *
     * @param auditRecord 审计记录DTO
     */
    void record(AuditRecordDTO auditRecord);

    /**
     * 异步记录审计日志
     *
     * @param auditLog 审计日志DTO
     */
    void logAsync(AuditLogDTO auditLog);

    /**
     * 异步记录审计记录
     *
     * @param auditRecord 审计记录DTO
     */
    void recordAsync(AuditRecordDTO auditRecord);
} 