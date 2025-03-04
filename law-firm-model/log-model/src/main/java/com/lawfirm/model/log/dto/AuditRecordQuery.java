package com.lawfirm.model.log.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计记录查询条件
 */
@Data
public class AuditRecordQuery {

    /**
     * 审计日志ID
     */
    private Long auditLogId;

    /**
     * 审计人ID
     */
    private Long auditorId;

    /**
     * 审计人名称
     */
    private String auditorName;

    /**
     * 审计动作
     */
    private String auditAction;

    /**
     * 审计结果（0通过 1不通过）
     */
    private Integer auditResult;

    /**
     * 审计开始时间
     */
    private LocalDateTime startTime;

    /**
     * 审计结束时间
     */
    private LocalDateTime endTime;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 