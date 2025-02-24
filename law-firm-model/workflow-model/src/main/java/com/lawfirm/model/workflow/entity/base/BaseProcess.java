package com.lawfirm.model.workflow.entity.base;

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
public class BaseProcess extends BaseModel {

    /**
     * 流程编号
     */
    private String processNo;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程类型
     * @see ProcessTypeEnum
     */
    private Integer processType;

    /**
     * 流程状态
     * @see ProcessStatusEnum
     */
    private Integer status;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 发起人ID
     */
    private Long initiatorId;

    /**
     * 发起人名称
     */
    private String initiatorName;

    /**
     * 发起时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 当前处理人ID
     */
    private Long currentHandlerId;

    /**
     * 当前处理人名称
     */
    private String currentHandlerName;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

    /**
     * 是否允许撤回 0-不允许 1-允许
     */
    private Integer allowRevoke;

    /**
     * 是否允许转办 0-不允许 1-允许
     */
    private Integer allowTransfer;

    /**
     * 流程配置（JSON格式）
     */
    private String processConfig;
} 