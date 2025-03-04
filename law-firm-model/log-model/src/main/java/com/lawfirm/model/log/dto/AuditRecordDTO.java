package com.lawfirm.model.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 审计记录DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuditRecordDTO extends BaseLogDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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
     * 审计意见
     */
    private String auditOpinion;

    /**
     * 审计时间
     */
    private LocalDateTime auditTime;

    /**
     * 审计节点
     */
    private String auditNode;
} 