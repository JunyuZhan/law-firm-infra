package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.entity.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件状态日志实体
 */
@Data
@TableName("case_status_log")
@EqualsAndHashCode(callSuper = true)
public class CaseStatusLog extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 原状态
     */
    @TableField("from_status")
    private CaseStatusEnum fromStatus;

    /**
     * 新状态
     */
    @TableField("to_status")
    private CaseStatusEnum toStatus;

    /**
     * 操作人
     */
    @TableField("operator")
    private String operator;

    /**
     * 变更原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 变更时间
     */
    @TableField("change_time")
    private LocalDateTime changeTime;

    /**
     * 关联的案件对象
     */
    @TableField(exist = false)
    private Case lawCase;
} 