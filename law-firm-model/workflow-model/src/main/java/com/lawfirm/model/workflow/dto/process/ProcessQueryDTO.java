package com.lawfirm.model.workflow.dto.process;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程查询DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
     * 流程状态
     */
    private Integer status;

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
     * 当前处理人ID
     */
    private Long currentHandlerId;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

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
     * 开始时间-起始
     */
    private String startTimeBegin;

    /**
     * 开始时间-结束
     */
    private String startTimeEnd;

    /**
     * 结束时间-起始
     */
    private String endTimeBegin;

    /**
     * 结束时间-结束
     */
    private String endTimeEnd;
} 