package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.enums.base.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 案件查询数据传输对象
 * 
 * 继承自PageDTO，包含查询案件时需要的条件，如案件编号、名称、类型、状态等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseQueryDTO extends PageDTO<CaseQueryDTO> {

    private static final long serialVersionUID = 1L;
    
    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 案件名称（模糊匹配）
     */
    private String caseName;

    /**
     * 案件描述（模糊匹配）
     */
    private String caseDescription;

    /**
     * 案件类型
     */
    private CaseTypeEnum caseType;

    /**
     * 案件类型列表
     */
    private List<CaseTypeEnum> caseTypes;

    /**
     * 案件状态
     */
    private CaseStatusEnum caseStatus;

    /**
     * 案件状态列表
     */
    private List<CaseStatusEnum> caseStatuses;

    /**
     * 案件进展
     */
    private CaseProgressEnum caseProgress;

    /**
     * 案件进展列表
     */
    private List<CaseProgressEnum> caseProgresses;

    /**
     * 案件阶段
     */
    private CaseStageEnum caseStage;

    /**
     * 案件阶段列表
     */
    private List<CaseStageEnum> caseStages;

    /**
     * 案件难度
     */
    private CaseDifficultyEnum caseDifficulty;

    /**
     * 案件难度列表
     */
    private List<CaseDifficultyEnum> caseDifficulties;

    /**
     * 案件重要性
     */
    private CaseImportanceEnum caseImportance;

    /**
     * 案件重要性列表
     */
    private List<CaseImportanceEnum> caseImportances;

    /**
     * 案件优先级
     */
    private CasePriorityEnum casePriority;

    /**
     * 案件优先级列表
     */
    private List<CasePriorityEnum> casePriorities;

    /**
     * 案件来源
     */
    private CaseSourceEnum caseSource;

    /**
     * 案件来源列表
     */
    private List<CaseSourceEnum> caseSources;

    /**
     * 办理方式
     */
    private CaseHandleTypeEnum handleType;

    /**
     * 办理方式列表
     */
    private List<CaseHandleTypeEnum> handleTypes;

    /**
     * 收费类型
     */
    private CaseFeeTypeEnum feeType;

    /**
     * 收费类型列表
     */
    private List<CaseFeeTypeEnum> feeTypes;

    /**
     * 预估金额最小值
     */
    private BigDecimal estimatedAmountMin;

    /**
     * 预估金额最大值
     */
    private BigDecimal estimatedAmountMax;

    /**
     * 实际金额最小值
     */
    private BigDecimal actualAmountMin;

    /**
     * 实际金额最大值
     */
    private BigDecimal actualAmountMax;

    /**
     * 委托人ID
     */
    private Long clientId;

    /**
     * 委托人名称（模糊匹配）
     */
    private String clientName;

    /**
     * 对方当事人（模糊匹配）
     */
    private String opposingParty;

    /**
     * 主办律师ID
     */
    private Long lawyerId;

    /**
     * 主办律师姓名（模糊匹配）
     */
    private String lawyerName;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称（模糊匹配）
     */
    private String departmentName;

    /**
     * 法院/仲裁机构（模糊匹配）
     */
    private String courtName;

    /**
     * 法官/仲裁员（模糊匹配）
     */
    private String judgeName;

    /**
     * 案号（模糊匹配）
     */
    private String courtCaseNumber;

    /**
     * 立案时间开始
     */
    private LocalDate filingTimeStart;

    /**
     * 立案时间结束
     */
    private LocalDate filingTimeEnd;

    /**
     * 结案时间开始
     */
    private LocalDate closingTimeStart;

    /**
     * 结案时间结束
     */
    private LocalDate closingTimeEnd;

    /**
     * 预计结案时间开始
     */
    private LocalDate expectedClosingTimeStart;

    /**
     * 预计结案时间结束
     */
    private LocalDate expectedClosingTimeEnd;

    /**
     * 案件结果
     */
    private CaseResultEnum caseResult;

    /**
     * 案件结果列表
     */
    private List<CaseResultEnum> caseResults;

    /**
     * 案件标签（模糊匹配）
     */
    private String caseTag;

    /**
     * 案件标签列表
     */
    private List<String> caseTags;

    /**
     * 是否已归档
     */
    private Boolean isArchived;

    /**
     * 是否已删除
     */
    private Boolean isDeleted;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名（模糊匹配）
     */
    private String creatorName;

    /**
     * 更新人ID
     */
    private Long updaterId;

    /**
     * 更新人姓名（模糊匹配）
     */
    private String updaterName;
} 