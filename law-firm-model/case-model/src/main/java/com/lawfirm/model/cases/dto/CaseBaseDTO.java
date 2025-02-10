package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CaseBaseDTO extends BaseDTO {

    @NotBlank(message = "案件编号不能为空")
    @Size(max = 50, message = "案件编号长度不能超过50个字符")
    private String caseNumber;

    @NotBlank(message = "案件名称不能为空")
    @Size(max = 200, message = "案件名称长度不能超过200个字符")
    private String caseName;

    @Size(max = 500, message = "案件描述长度不能超过500个字符")
    private String description;

    @NotNull(message = "案件类型不能为空")
    private CaseTypeEnum caseType;

    @NotNull(message = "案件状态不能为空")
    private CaseStatusEnum caseStatus;

    @NotNull(message = "案件进展不能为空")
    private CaseProgressEnum caseProgress;

    @NotNull(message = "办理方式不能为空")
    private CaseHandleTypeEnum caseHandleType;

    private CaseDifficultyEnum caseDifficulty;

    private CaseImportanceEnum caseImportance;

    private CasePriorityEnum casePriority;

    private CaseFeeTypeEnum caseFeeType;

    private CaseSourceEnum caseSource;

    @NotBlank(message = "主办律师不能为空")
    @Size(max = 50, message = "主办律师长度不能超过50个字符")
    private String lawyer;

    @NotNull(message = "委托人ID不能为空")
    private Long clientId;

    private Long lawFirmId;

    private Long branchId;

    private Long departmentId;

    private LocalDateTime filingTime;

    private LocalDateTime closingTime;

    private BigDecimal fee;

    private String courtName;

    private String judgeName;

    private String courtCaseNumber;

    private Boolean isMajor = false;

    private Boolean hasConflict = false;

    private String conflictReason;
} 