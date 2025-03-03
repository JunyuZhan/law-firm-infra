package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件工作量数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseWorkloadDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 律师ID
     */
    private Long lawyerId;

    /**
     * 律师姓名
     */
    private String lawyerName;

    /**
     * 工作类型
     */
    private Integer workType;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 工作开始时间
     */
    private transient LocalDateTime startTime;

    /**
     * 工作结束时间
     */
    private transient LocalDateTime endTime;

    /**
     * 工作时长（小时）
     */
    private BigDecimal workHours;

    /**
     * 工作进度（0-100）
     */
    private Integer progress;

    /**
     * 工作成果
     */
    private String workResult;

    /**
     * 工作备注
     */
    private String workNote;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核时间
     */
    private transient LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 小时费率
     */
    private BigDecimal hourlyRate;

    /**
     * 费用金额
     */
    private BigDecimal feeAmount;

    /**
     * 是否已计费
     */
    private Boolean isBilled;

    /**
     * 是否可编辑
     */
    private Boolean isEditable;
} 