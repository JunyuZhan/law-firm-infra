package com.lawfirm.model.log.entity.audit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.entity.BaseEntity;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 审计日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_audit_log")
public class AuditLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人ID
     */
    @TableField("operator_id")
    private Long operatorId;

    /**
     * 操作人名称
     */
    @TableField("operator_name")
    private String operatorName;

    /**
     * 操作人IP
     */
    @TableField("operator_ip")
    private String operatorIp;

    /**
     * 操作类型
     */
    @TableField("operate_type")
    private OperateTypeEnum operateType;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private BusinessTypeEnum businessType;

    /**
     * 操作模块
     */
    @TableField("module")
    private String module;

    /**
     * 操作描述
     */
    @TableField("description")
    private String description;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField("operation_time")
    private LocalDateTime operationTime;

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
} 