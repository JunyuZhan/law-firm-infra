package com.lawfirm.model.workflow.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.workflow.enums.ProcessStatusEnum;
import com.lawfirm.model.workflow.enums.ProcessTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 流程基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_process")
public class BaseProcess extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 流程编号
     */
    @TableField("process_no")
    private String processNo;

    /**
     * 流程名称
     */
    @TableField("process_name")
    private String processName;

    /**
     * 流程类型
     * @see ProcessTypeEnum
     */
    @TableField("process_type")
    private Integer processType;

    /**
     * 流程状态
     * @see ProcessStatusEnum
     */
    @TableField("status")
    private Integer status;

    /**
     * 流程描述
     */
    @TableField("description")
    private String description;

    /**
     * 业务ID
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 发起人ID
     */
    @TableField("initiator_id")
    private Long initiatorId;

    /**
     * 发起人名称
     */
    @TableField("initiator_name")
    private String initiatorName;

    /**
     * 发起时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 当前处理人ID
     */
    @TableField("current_handler_id")
    private Long currentHandlerId;

    /**
     * 当前处理人名称
     */
    @TableField("current_handler_name")
    private String currentHandlerName;

    /**
     * 优先级 1-低 2-中 3-高
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 是否允许撤回 0-不允许 1-允许
     */
    @TableField("allow_revoke")
    private Integer allowRevoke;

    /**
     * 是否允许转办 0-不允许 1-允许
     */
    @TableField("allow_transfer")
    private Integer allowTransfer;

    /**
     * 流程配置（JSON格式）
     */
    @TableField("process_config")
    private String processConfig;
} 