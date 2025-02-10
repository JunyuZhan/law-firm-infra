package com.lawfirm.model.cases.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseCreateDTO extends BaseDTO {
    
    @NotBlank(message = "案件编号不能为空")
    @Size(max = 50, message = "案件编号长度不能超过50")
    private String caseNumber;
    
    @NotBlank(message = "案件名称不能为空")
    @Size(max = 200, message = "案件名称长度不能超过200")
    private String caseName;
    
    @Size(max = 500, message = "案件描述长度不能超过500")
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
    private String lawyer;
    
    @NotNull(message = "客户ID不能为空")
    private Long clientId;
    
    @NotNull(message = "律所ID不能为空")
    private Long lawFirmId;
    
    private Long branchId;
    
    private Long departmentId;
    
    @NotNull(message = "立案时间不能为空")
    private LocalDateTime filingTime;
    
    private LocalDateTime closingTime;
    
    private BigDecimal fee;
    
    private String courtName;
    
    private String judgeName;
    
    private String courtCaseNumber;
    
    private Boolean isMajor;
    
    private Boolean hasConflict;
    
    private String conflictReason;
    
    @Size(max = 500, message = "立案申请书长度不能超过500个字符")
    private String filingApplication;

    @Valid
    private List<CaseParticipantDTO> participants;

    @Valid
    private List<CaseDocumentDTO> documents;

    @NotEmpty(message = "至少需要一个案件团队成员")
    @Valid
    private List<CaseTeamDTO> teamMembers;

    private List<CaseReminderDTO> reminders;

    @Size(max = 500, message = "特殊要求长度不能超过500个字符")
    private String specialRequirements;

    private Boolean needConflictCheck = true;

    private Boolean needApproval = false;

    private String approvalFlow;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    @Override
    public CaseCreateDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseCreateDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseCreateDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseCreateDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 