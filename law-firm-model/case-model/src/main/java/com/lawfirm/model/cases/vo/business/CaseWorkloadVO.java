package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件工作量视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseWorkloadVO extends BaseVO {

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
     * 案件名称
     */
    private String caseName;

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
     * 工作类型名称
     */
    private String workTypeName;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

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
     * 审核状态名称
     */
    private String reviewStatusName;

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
    private LocalDateTime reviewTime;

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

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 最后修改人姓名
     */
    private String lastModifierName;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 工作地点
     */
    private String workLocation;

    /**
     * 关联文档数量
     */
    private Integer documentCount;

    /**
     * 获取工作时长的格式化字符串
     */
    public String getFormattedWorkHours() {
        if (workHours == null) {
            return "0小时";
        }
        return workHours.stripTrailingZeros().toPlainString() + "小时";
    }

    /**
     * 获取费用金额的格式化字符串
     */
    public String getFormattedFeeAmount() {
        if (feeAmount == null) {
            return "¥0.00";
        }
        return "¥" + feeAmount.setScale(2, java.math.RoundingMode.HALF_UP).toString();
    }

    /**
     * 判断是否超时
     */
    public boolean isOvertime() {
        if (endTime == null) {
            return false;
        }
        return endTime.isBefore(LocalDateTime.now());
    }

    /**
     * 判断是否待审核
     */
    public boolean isPendingReview() {
        return reviewStatus != null && reviewStatus == 0;
    }

    /**
     * 判断是否已审核通过
     */
    public boolean isReviewApproved() {
        return reviewStatus != null && reviewStatus == 1;
    }

    /**
     * 判断是否已审核拒绝
     */
    public boolean isReviewRejected() {
        return reviewStatus != null && reviewStatus == 2;
    }

    /**
     * 获取工作进度的格式化字符串
     */
    public String getFormattedPercentage() {
        if (progress == null) {
            return "0.00%";
        }
        return progress.toString() + "%";
    }
} 