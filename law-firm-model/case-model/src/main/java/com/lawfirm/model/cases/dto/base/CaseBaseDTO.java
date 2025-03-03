package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.base.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 案件基础数据传输对象
 * 
 * 包含案件的基本信息，如案件编号、名称、类型、状态等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseBaseDTO extends BaseDTO {

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
     * 案件描述
     */
    private String caseDescription;

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
     * 案件来源
     */
    private CaseSourceEnum caseSource;

    /**
     * 办理方式
     */
    private CaseHandleTypeEnum handleType;

    /**
     * 收费类型
     */
    private CaseFeeTypeEnum feeType;

    /**
     * 预估金额
     */
    private BigDecimal estimatedAmount;

    /**
     * 实际金额
     */
    private BigDecimal actualAmount;

    /**
     * 委托人ID
     */
    private Long clientId;

    /**
     * 委托人名称
     */
    private String clientName;

    /**
     * 对方当事人
     */
    private String opposingParty;

    /**
     * 主办律师ID
     */
    private Long lawyerId;

    /**
     * 主办律师姓名
     */
    private String lawyerName;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 法院/仲裁机构
     */
    private String courtName;

    /**
     * 法官/仲裁员
     */
    private String judgeName;

    /**
     * 案号
     */
    private String courtCaseNumber;

    /**
     * 立案时间
     */
    private transient LocalDate filingTime;

    /**
     * 结案时间
     */
    private transient LocalDate closingTime;

    /**
     * 预计结案时间
     */
    private transient LocalDate expectedClosingTime;

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
     * 冲突说明
     */
    private String conflictDescription;

    /**
     * 是否需要保密
     */
    private Boolean isConfidential;

    /**
     * 保密级别
     */
    private Integer confidentialLevel;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 租户ID
     */
    private Long tenantId;
} 