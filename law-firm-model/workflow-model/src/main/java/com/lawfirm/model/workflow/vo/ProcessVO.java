package com.lawfirm.model.workflow.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 流程视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessVO extends BaseVO {

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
     */
    private Integer processType;

    /**
     * 流程类型名称
     */
    private String processTypeName;

    /**
     * 流程状态
     */
    private Integer status;

    /**
     * 流程状态名称
     */
    private String statusName;

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
     * 优先级名称
     */
    private String priorityName;

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

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程标签
     */
    private String tags;

    /**
     * 流程模板ID
     */
    private Long templateId;

    /**
     * 流程模板版本
     */
    private String templateVersion;

    /**
     * 流程变量（JSON格式）
     */
    private String variables;

    /**
     * 流程表单（JSON格式）
     */
    private String formData;

    /**
     * 流程附件IDs
     */
    private String attachmentIds;

    /**
     * 流程备注
     */
    private String notes;
} 