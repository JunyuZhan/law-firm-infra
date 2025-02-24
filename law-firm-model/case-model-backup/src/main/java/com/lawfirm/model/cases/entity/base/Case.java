package com.lawfirm.model.cases.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.constants.CaseFieldConstants;
import com.lawfirm.model.cases.enums.base.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件实体类
 */
@Data
@Entity
@Table(name = "case_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_case")
public class Case extends ModelBaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件编号
     */
    @NotBlank(message = "案件编号不能为空")
    @Size(min = CaseFieldConstants.CASE_NUMBER_MIN_LENGTH, max = CaseFieldConstants.CASE_NUMBER_MAX_LENGTH, message = "案件编号长度必须在{min}到{max}之间")
    @Pattern(regexp = CaseFieldConstants.CASE_NUMBER_PATTERN, message = "案件编号格式不正确")
    @TableField("case_number")
    @Comment("案件编号")
    private String caseNumber;

    /**
     * 案件名称
     */
    @NotBlank(message = "案件名称不能为空")
    @Size(min = CaseFieldConstants.CASE_NAME_MIN_LENGTH, max = CaseFieldConstants.CASE_NAME_MAX_LENGTH, message = "案件名称长度必须在{min}到{max}之间")
    @TableField("case_name")
    @Comment("案件名称")
    private String caseName;

    /**
     * 案件描述
     */
    @Size(max = CaseFieldConstants.CASE_DESCRIPTION_MAX_LENGTH, message = "案件描述长度不能超过{max}个字符")
    @TableField("description")
    @Comment("案件描述")
    private String description;

    /**
     * 案件类型
     */
    @NotNull(message = "案件类型不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_type")
    @Comment("案件类型")
    private CaseTypeEnum caseType;

    /**
     * 案件状态
     */
    @NotNull(message = "案件状态不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_status")
    @Comment("案件状态")
    private CaseStatusEnum caseStatus;

    /**
     * 案件进展
     */
    @NotNull(message = "案件进度不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_progress")
    @Comment("案件进度")
    private CaseProgressEnum caseProgress;

    /**
     * 办理方式
     */
    @NotNull(message = "办理方式不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_handle_type")
    @Comment("办理方式")
    private CaseHandleTypeEnum caseHandleType;

    /**
     * 难度等级
     */
    @NotNull(message = "案件难度不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_difficulty")
    @Comment("案件难度")
    private CaseDifficultyEnum caseDifficulty;

    /**
     * 重要程度
     */
    @NotNull(message = "案件重要程度不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_importance")
    @Comment("案件重要程度")
    private CaseImportanceEnum caseImportance;

    /**
     * 优先级
     */
    @NotNull(message = "案件优先级不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_priority")
    @Comment("案件优先级")
    private CasePriorityEnum casePriority;

    /**
     * 收费方式
     */
    @NotNull(message = "收费类型不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_fee_type")
    @Comment("收费类型")
    private CaseFeeTypeEnum caseFeeType;

    /**
     * 案件来源
     */
    @NotNull(message = "案件来源不能为空")
    @Enumerated(EnumType.STRING)
    @TableField("case_source")
    @Comment("案件来源")
    private CaseSourceEnum caseSource;

    /**
     * 主办律师ID（关联personnel-model）
     */
    @NotBlank(message = "承办律师不能为空")
    @TableField("lawyer_name")
    @Comment("承办律师")
    private String lawyerName;

    /**
     * 操作人ID（关联personnel-model）
     */
    @NotBlank(message = "操作人不能为空")
    @TableField("operator")
    @Comment("操作人")
    private String operator;

    /**
     * 委托人ID（关联client-model）
     */
    @NotNull(message = "客户ID不能为空")
    @TableField("client_id")
    @Comment("客户ID")
    private Long clientId;

    /**
     * 律所ID（关联organization-model）
     */
    @NotNull(message = "律所ID不能为空")
    @TableField("law_firm_id")
    @Comment("律所ID")
    private Long lawFirmId;

    /**
     * 分所ID（关联organization-model）
     */
    @TableField("branch_id")
    @Comment("分所ID")
    private Long branchId;

    /**
     * 部门ID（关联organization-model）
     */
    @TableField("department_id")
    @Comment("部门ID")
    private Long departmentId;

    /**
     * 立案时间
     */
    @NotNull(message = "立案时间不能为空")
    @TableField("filing_time")
    @Comment("立案时间")
    private LocalDateTime filingTime;

    /**
     * 结案时间
     */
    @TableField("closing_time")
    @Comment("结案时间")
    private LocalDateTime closingTime;

    /**
     * 收费金额
     */
    @TableField("fee")
    @Comment("收费金额")
    private BigDecimal fee;

    /**
     * 法院名称
     */
    @Size(max = CaseFieldConstants.COURT_NAME_MAX_LENGTH, message = "法院名称长度不能超过{max}个字符")
    @TableField("court_name")
    @Comment("法院名称")
    private String courtName;

    /**
     * 法官姓名
     */
    @Size(max = CaseFieldConstants.JUDGE_NAME_MAX_LENGTH, message = "法官姓名长度不能超过{max}个字符")
    @TableField("judge_name")
    @Comment("法官姓名")
    private String judgeName;

    /**
     * 法院案号
     */
    @Size(max = CaseFieldConstants.COURT_CASE_NUMBER_MAX_LENGTH, message = "法院案号长度不能超过{max}个字符")
    @TableField("court_case_number")
    @Comment("法院案号")
    private String courtCaseNumber;

    /**
     * 是否重大案件
     */
    @NotNull(message = "是否重大案件不能为空")
    @TableField("is_major")
    @Comment("是否重大案件")
    private Boolean isMajor;

    /**
     * 是否有利益冲突
     */
    @NotNull(message = "是否存在利益冲突不能为空")
    @TableField("has_conflict")
    @Comment("是否存在利益冲突")
    private Boolean hasConflict;

    /**
     * 利益冲突原因
     */
    @Size(max = CaseFieldConstants.CONFLICT_REASON_MAX_LENGTH, message = "利益冲突原因长度不能超过{max}个字符")
    @TableField("conflict_reason")
    @Comment("利益冲突原因")
    private String conflictReason;

    /**
     * 备注
     */
    @Size(max = CaseFieldConstants.CASE_REMARK_MAX_LENGTH, message = "备注长度不能超过{max}个字符")
    @TableField("remark")
    @Comment("备注")
    private String remark;

    /**
     * 是否归档
     */
    @TableField("is_archived")
    @Comment("是否归档")
    private Boolean archived;

    /**
     * 归档时间
     */
    @TableField("archive_time")
    @Comment("归档时间")
    private LocalDateTime archiveTime;

    /**
     * 归档人ID
     */
    @TableField("archive_by")
    @Comment("归档人ID")
    private Long archiveBy;
} 