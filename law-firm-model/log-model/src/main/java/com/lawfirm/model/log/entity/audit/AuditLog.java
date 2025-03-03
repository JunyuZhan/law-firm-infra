package com.lawfirm.model.log.entity.audit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.log.entity.base.AuditableLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 审计日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_audit_log")
public class AuditLog extends AuditableLog {

    private static final long serialVersionUID = 1L;

    /**
     * 审计对象ID
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 审计对象类型
     */
    @TableField("target_type")
    private String targetType;

    /**
     * 审计对象名称
     */
    @TableField("target_name")
    private String targetName;

    /**
     * 操作前数据
     */
    @TableField("before_data")
    private String beforeData;

    /**
     * 操作后数据
     */
    @TableField("after_data")
    private String afterData;

    /**
     * 变更项
     */
    @TableField("changed_fields")
    private String changedFields;

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
} 