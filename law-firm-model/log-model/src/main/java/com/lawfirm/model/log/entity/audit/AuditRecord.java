package com.lawfirm.model.log.entity.audit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 审计记录实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_audit_record")
public class AuditRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审计日志ID
     */
    @TableField("audit_log_id")
    private Long auditLogId;

    /**
     * 审计人ID
     */
    @TableField("auditor_id")
    private Long auditorId;

    /**
     * 审计人名称
     */
    @TableField("auditor_name")
    private String auditorName;

    /**
     * 审计动作（如：提交、审核、驳回等）
     */
    @TableField("audit_action")
    private String auditAction;

    /**
     * 审计结果（0通过 1不通过）
     */
    @TableField("audit_result")
    private Integer auditResult;

    /**
     * 审计意见
     */
    @TableField("audit_opinion")
    private String auditOpinion;

    /**
     * 审计时间
     */
    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 审计节点
     */
    @TableField("audit_node")
    private String auditNode;

    /**
     * 下一个审计节点
     */
    @TableField("next_node")
    private String nextNode;
} 