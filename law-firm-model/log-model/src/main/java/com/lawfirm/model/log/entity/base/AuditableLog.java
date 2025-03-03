package com.lawfirm.model.log.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 可审计日志基类
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class AuditableLog extends BaseLog {

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
     * 操作状态（0正常 1异常）
     */
    @TableField("status")
    private Integer status;

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
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;
} 