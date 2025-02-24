package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.constants.CaseFieldConstants;
import com.lawfirm.model.cases.enums.base.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件基础DTO
 */
@Data
@Schema(description = "案件基础DTO")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseBaseDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "案件编号")
    @NotBlank(message = "案件编号不能为空")
    @Size(min = CaseFieldConstants.CASE_NUMBER_MIN_LENGTH, 
          max = CaseFieldConstants.CASE_NUMBER_MAX_LENGTH, 
          message = "案件编号长度必须在{min}到{max}之间")
    @Pattern(regexp = CaseFieldConstants.CASE_NUMBER_PATTERN, 
             message = "案件编号格式不正确")
    private String caseNumber;

    @Schema(description = "案件名称")
    @NotBlank(message = "案件名称不能为空")
    @Size(min = CaseFieldConstants.CASE_NAME_MIN_LENGTH, 
          max = CaseFieldConstants.CASE_NAME_MAX_LENGTH, 
          message = "案件名称长度必须在{min}到{max}之间")
    private String caseName;

    @Schema(description = "案件描述")
    @Size(max = CaseFieldConstants.CASE_DESCRIPTION_MAX_LENGTH, 
          message = "案件描述长度不能超过{max}个字符")
    private String description;

    @Schema(description = "案件类型")
    @NotNull(message = "案件类型不能为空")
    private CaseTypeEnum caseType;

    @Schema(description = "案件状态")
    @NotNull(message = "案件状态不能为空")
    private CaseStatusEnum caseStatus;

    @Schema(description = "案件进展")
    @NotNull(message = "案件进展不能为空")
    private CaseProgressEnum caseProgress;

    @Schema(description = "办理方式")
    @NotNull(message = "办理方式不能为空")
    private CaseHandleTypeEnum caseHandleType;

    @Schema(description = "难度等级")
    private CaseDifficultyEnum caseDifficulty;

    @Schema(description = "重要程度")
    private CaseImportanceEnum caseImportance;

    @Schema(description = "优先级")
    private CasePriorityEnum casePriority;

    @Schema(description = "收费类型")
    private CaseFeeTypeEnum caseFeeType;

    @Schema(description = "案件来源")
    private CaseSourceEnum caseSource;

    @Schema(description = "主办律师")
    @NotBlank(message = "主办律师不能为空")
    @Size(max = CaseFieldConstants.LAWYER_NAME_MAX_LENGTH, 
          message = "律师姓名长度不能超过{max}个字符")
    private String lawyer;

    @Schema(description = "委托人ID")
    @NotNull(message = "委托人ID不能为空")
    private Long clientId;

    @Schema(description = "委托人名称")
    private String clientName;

    @Schema(description = "律所ID")
    private Long lawFirmId;

    @Schema(description = "律所名称")
    private String lawFirmName;

    @Schema(description = "分所ID")
    private Long branchId;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "立案时间")
    private LocalDateTime filingTime;

    @Schema(description = "结案时间")
    private LocalDateTime closingTime;

    @Schema(description = "收费金额")
    @Pattern(regexp = CaseFieldConstants.CASE_FEE_PATTERN, 
             message = "费用格式不正确")
    private BigDecimal fee;

    @Schema(description = "法院名称")
    @Size(max = CaseFieldConstants.COURT_NAME_MAX_LENGTH, 
          message = "法院名称长度不能超过{max}个字符")
    private String court;

    @Schema(description = "法官姓名")
    @Size(max = CaseFieldConstants.JUDGE_NAME_MAX_LENGTH, 
          message = "法官姓名长度不能超过{max}个字符")
    private String judgeInfo;

    @Schema(description = "法院案号")
    @Size(max = CaseFieldConstants.COURT_CASE_NUMBER_MAX_LENGTH, 
          message = "法院案号长度不能超过{max}个字符")
    private String courtCaseNumber;

    @Schema(description = "是否重大案件")
    private Boolean isMajor = false;

    @Schema(description = "是否有利益冲突")
    private Boolean hasConflict = false;

    @Schema(description = "利益冲突原因")
    @Size(max = CaseFieldConstants.CONFLICT_REASON_MAX_LENGTH, 
          message = "利益冲突原因长度不能超过{max}个字符")
    private String conflictReason;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updateBy;
} 