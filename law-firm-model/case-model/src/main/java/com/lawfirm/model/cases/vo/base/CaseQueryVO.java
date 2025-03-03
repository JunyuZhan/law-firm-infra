package com.lawfirm.model.cases.vo.base;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.base.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 案件查询视图对象
 * 
 * 包含列表展示所需的基本信息，如案件编号、名称、类型、状态等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseQueryVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    
    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 案件类型
     */
    private CaseTypeEnum caseType;

    /**
     * 案件状态
     */
    private CaseStatusEnum caseStatus;

    /**
     * 案件进展
     */
    private CaseProgressEnum caseProgress;

    /**
     * 案件阶段
     */
    private CaseStageEnum caseStage;

    /**
     * 案件难度
     */
    private CaseDifficultyEnum caseDifficulty;

    /**
     * 案件重要性
     */
    private CaseImportanceEnum caseImportance;

    /**
     * 案件优先级
     */
    private CasePriorityEnum casePriority;

    /**
     * 预估金额
     */
    private BigDecimal estimatedAmount;

    /**
     * 实际金额
     */
    private BigDecimal actualAmount;

    /**
     * 委托人名称
     */
    private String clientName;

    /**
     * 对方当事人
     */
    private String opposingParty;

    /**
     * 主办律师姓名
     */
    private String lawyerName;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 法院/仲裁机构
     */
    private String courtName;

    /**
     * 案号
     */
    private String courtCaseNumber;

    /**
     * 立案时间
     */
    private LocalDate filingTime;

    /**
     * 结案时间
     */
    private LocalDate closingTime;

    /**
     * 预计结案时间
     */
    private LocalDate expectedClosingTime;

    /**
     * 案件结果
     */
    private CaseResultEnum caseResult;

    /**
     * 案件标签，多个标签以逗号分隔
     */
    private String caseTags;

    /**
     * 是否有利益冲突
     */
    private Boolean hasConflict;

    /**
     * 是否需要保密
     */
    private Boolean isConfidential;

    /**
     * 最后操作时间
     */
    private LocalDateTime lastOperationTime;

    /**
     * 最后操作人姓名
     */
    private String lastOperatorName;

    /**
     * 获取标签数组
     */
    public String[] getTagArray() {
        if (this.caseTags == null || this.caseTags.isEmpty()) {
            return new String[0];
        }
        return this.caseTags.split(",");
    }

    /**
     * 判断案件是否已结案
     */
    public boolean isClosed() {
        return this.caseStatus != null && 
               this.caseStatus == CaseStatusEnum.CLOSED;
    }

    /**
     * 判断案件是否为高优先级
     */
    public boolean isHighPriority() {
        return this.casePriority != null && 
               this.casePriority.isHighPriority();
    }

    /**
     * 判断案件是否为高难度
     */
    public boolean isHighDifficulty() {
        return this.caseDifficulty != null && 
               this.caseDifficulty.isHighDifficulty();
    }

    /**
     * 判断案件是否为高重要性
     */
    public boolean isHighImportance() {
        return this.caseImportance != null && 
               this.caseImportance.isHighImportance();
    }

    /**
     * 计算案件已处理天数
     */
    public long getProcessedDays() {
        LocalDate start = this.filingTime != null ? this.filingTime : 
                         (this.getCreateTime() != null ? this.getCreateTime().toLocalDate() : LocalDate.now());
        LocalDate end = this.closingTime != null ? this.closingTime : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }
} 