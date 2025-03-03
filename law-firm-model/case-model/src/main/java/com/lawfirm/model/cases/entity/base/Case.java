package com.lawfirm.model.cases.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.cases.enums.base.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 案件实体类
 * 
 * 案件是律师事务所管理系统的核心业务对象，记录了案件的基本信息、
 * 当事人信息、时间信息、案件属性、案件描述、费用信息等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_info")
public class Case extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 案件名称
     */
    @TableField("case_name")
    private String caseName;

    /**
     * 案件描述
     */
    @TableField("case_description")
    private String caseDescription;

    /**
     * 案件类型
     */
    @TableField("case_type")
    private Integer caseType;

    /**
     * 案件状态
     */
    @TableField("case_status")
    private Integer caseStatus;

    /**
     * 案件进展
     */
    @TableField("case_progress")
    private Integer caseProgress;

    /**
     * 案件阶段
     */
    @TableField("case_stage")
    private Integer caseStage;

    /**
     * 案件难度
     */
    @TableField("case_difficulty")
    private Integer caseDifficulty;

    /**
     * 案件重要性
     */
    @TableField("case_importance")
    private Integer caseImportance;

    /**
     * 案件优先级
     */
    @TableField("case_priority")
    private Integer casePriority;

    /**
     * 案件来源
     */
    @TableField("case_source")
    private Integer caseSource;

    /**
     * 办理方式
     */
    @TableField("handle_type")
    private Integer handleType;

    /**
     * 收费类型
     */
    @TableField("fee_type")
    private Integer feeType;

    /**
     * 预估金额
     */
    @TableField("estimated_amount")
    private BigDecimal estimatedAmount;

    /**
     * 实际金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;

    /**
     * 委托人ID
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 委托人名称
     */
    @TableField("client_name")
    private String clientName;

    /**
     * 对方当事人
     */
    @TableField("opposing_party")
    private String opposingParty;

    /**
     * 主办律师ID
     */
    @TableField("lawyer_id")
    private Long lawyerId;

    /**
     * 主办律师姓名
     */
    @TableField("lawyer_name")
    private String lawyerName;

    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @TableField("department_name")
    private String departmentName;

    /**
     * 法院/仲裁机构
     */
    @TableField("court_name")
    private String courtName;

    /**
     * 法官/仲裁员
     */
    @TableField("judge_name")
    private String judgeName;

    /**
     * 案号
     */
    @TableField("court_case_number")
    private String courtCaseNumber;

    /**
     * 立案时间
     */
    @TableField("filing_time")
    private LocalDate filingTime;

    /**
     * 结案时间
     */
    @TableField("closing_time")
    private LocalDate closingTime;

    /**
     * 预计结案时间
     */
    @TableField("expected_closing_time")
    private LocalDate expectedClosingTime;

    /**
     * 案件结果
     */
    @TableField("case_result")
    private Integer caseResult;

    /**
     * 案件标签，多个标签以逗号分隔
     */
    @TableField("case_tags")
    private String caseTags;

    /**
     * 是否有利益冲突
     */
    @TableField("has_conflict")
    private Boolean hasConflict;

    /**
     * 冲突说明
     */
    @TableField("conflict_description")
    private String conflictDescription;

    /**
     * 是否需要保密
     */
    @TableField("is_confidential")
    private Boolean isConfidential;

    /**
     * 保密级别
     */
    @TableField("confidential_level")
    private Integer confidentialLevel;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 最后操作时间
     */
    @TableField("last_operation_time")
    private transient LocalDateTime lastOperationTime;

    /**
     * 最后操作人ID
     */
    @TableField("last_operator_id")
    private Long lastOperatorId;

    /**
     * 最后操作人姓名
     */
    @TableField("last_operator_name")
    private String lastOperatorName;

    /**
     * 获取案件类型枚举
     */
    public CaseTypeEnum getCaseTypeEnum() {
        return CaseTypeEnum.valueOf(this.caseType);
    }

    /**
     * 获取案件状态枚举
     */
    public CaseStatusEnum getCaseStatusEnum() {
        return CaseStatusEnum.valueOf(this.caseStatus);
    }

    /**
     * 获取案件进展枚举
     */
    public CaseProgressEnum getCaseProgressEnum() {
        return CaseProgressEnum.valueOf(this.caseProgress);
    }

    /**
     * 获取案件阶段枚举
     */
    public CaseStageEnum getCaseStageEnum() {
        return CaseStageEnum.valueOf(this.caseStage);
    }

    /**
     * 获取案件难度枚举
     */
    public CaseDifficultyEnum getCaseDifficultyEnum() {
        return CaseDifficultyEnum.valueOf(this.caseDifficulty);
    }

    /**
     * 获取案件重要性枚举
     */
    public CaseImportanceEnum getCaseImportanceEnum() {
        return CaseImportanceEnum.valueOf(this.caseImportance);
    }

    /**
     * 获取案件优先级枚举
     */
    public CasePriorityEnum getCasePriorityEnum() {
        return CasePriorityEnum.valueOf(this.casePriority);
    }

    /**
     * 获取案件来源枚举
     */
    public CaseSourceEnum getCaseSourceEnum() {
        return CaseSourceEnum.valueOf(this.caseSource);
    }

    /**
     * 获取办理方式枚举
     */
    public CaseHandleTypeEnum getHandleTypeEnum() {
        return CaseHandleTypeEnum.valueOf(this.handleType);
    }

    /**
     * 获取收费类型枚举
     */
    public CaseFeeTypeEnum getFeeTypeEnum() {
        return CaseFeeTypeEnum.valueOf(this.feeType);
    }

    /**
     * 获取案件结果枚举
     */
    public CaseResultEnum getCaseResultEnum() {
        return CaseResultEnum.valueOf(this.caseResult);
    }

    /**
     * 设置案件类型
     */
    public Case setCaseTypeEnum(CaseTypeEnum caseTypeEnum) {
        this.caseType = caseTypeEnum != null ? caseTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件状态
     */
    public Case setCaseStatusEnum(CaseStatusEnum caseStatusEnum) {
        this.caseStatus = caseStatusEnum != null ? caseStatusEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件进展
     */
    public Case setCaseProgressEnum(CaseProgressEnum caseProgressEnum) {
        this.caseProgress = caseProgressEnum != null ? caseProgressEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件阶段
     */
    public Case setCaseStageEnum(CaseStageEnum caseStageEnum) {
        this.caseStage = caseStageEnum != null ? caseStageEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件难度
     */
    public Case setCaseDifficultyEnum(CaseDifficultyEnum caseDifficultyEnum) {
        this.caseDifficulty = caseDifficultyEnum != null ? caseDifficultyEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件重要性
     */
    public Case setCaseImportanceEnum(CaseImportanceEnum caseImportanceEnum) {
        this.caseImportance = caseImportanceEnum != null ? caseImportanceEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件优先级
     */
    public Case setCasePriorityEnum(CasePriorityEnum casePriorityEnum) {
        this.casePriority = casePriorityEnum != null ? casePriorityEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件来源
     */
    public Case setCaseSourceEnum(CaseSourceEnum caseSourceEnum) {
        this.caseSource = caseSourceEnum != null ? caseSourceEnum.getValue() : null;
        return this;
    }

    /**
     * 设置办理方式
     */
    public Case setHandleTypeEnum(CaseHandleTypeEnum handleTypeEnum) {
        this.handleType = handleTypeEnum != null ? handleTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 设置收费类型
     */
    public Case setFeeTypeEnum(CaseFeeTypeEnum feeTypeEnum) {
        this.feeType = feeTypeEnum != null ? feeTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 设置案件结果
     */
    public Case setCaseResultEnum(CaseResultEnum caseResultEnum) {
        this.caseResult = caseResultEnum != null ? caseResultEnum.getValue() : null;
        return this;
    }

    /**
     * 判断案件是否已结案
     */
    public boolean isClosed() {
        return this.caseStatus != null && 
               this.getCaseStatusEnum() == CaseStatusEnum.CLOSED;
    }

    /**
     * 判断案件是否为高优先级
     */
    public boolean isHighPriority() {
        return this.casePriority != null && 
               this.getCasePriorityEnum().isHighPriority();
    }

    /**
     * 判断案件是否为高难度
     */
    public boolean isHighDifficulty() {
        return this.caseDifficulty != null && 
               this.getCaseDifficultyEnum().isHighDifficulty();
    }

    /**
     * 判断案件是否为高重要性
     */
    public boolean isHighImportance() {
        return this.caseImportance != null && 
               this.getCaseImportanceEnum().isHighImportance();
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

    /**
     * 获取案件标签列表
     */
    public String[] getTagArray() {
        if (this.caseTags == null || this.caseTags.isEmpty()) {
            return new String[0];
        }
        return this.caseTags.split(",");
    }

    /**
     * 添加案件标签
     */
    public Case addTag(String tag) {
        if (tag == null || tag.isEmpty()) {
            return this;
        }
        
        if (this.caseTags == null || this.caseTags.isEmpty()) {
            this.caseTags = tag;
        } else if (!this.caseTags.contains(tag)) {
            this.caseTags = this.caseTags + "," + tag;
        }
        
        return this;
    }

    /**
     * 移除案件标签
     */
    public Case removeTag(String tag) {
        if (tag == null || tag.isEmpty() || this.caseTags == null || this.caseTags.isEmpty()) {
            return this;
        }
        
        String[] tags = this.caseTags.split(",");
        StringBuilder sb = new StringBuilder();
        
        for (String t : tags) {
            if (!t.equals(tag)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(t);
            }
        }
        
        this.caseTags = sb.toString();
        return this;
    }
}