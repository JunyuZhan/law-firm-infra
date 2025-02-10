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
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CaseUpdateDTO extends CaseBaseDTO {
    
    @NotNull(message = "案件ID不能为空")
    private Long id;
    
    @Size(max = 200, message = "案件名称长度不能超过200")
    private String caseName;
    
    @Size(max = 500, message = "案件描述长度不能超过500")
    private String description;
    
    private CaseTypeEnum caseType;
    
    private CaseStatusEnum caseStatus;
    
    private CaseProgressEnum caseProgress;
    
    private CaseHandleTypeEnum caseHandleType;
    
    private CaseDifficultyEnum caseDifficulty;
    
    private CaseImportanceEnum caseImportance;
    
    private CasePriorityEnum casePriority;
    
    private CaseFeeTypeEnum caseFeeType;
    
    private CaseSourceEnum caseSource;
    
    private String lawyer;
    
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
    
    private Boolean isMajor;
    
    private Boolean hasConflict;
    
    private String conflictReason;
    
    @Size(max = 500, message = "变更原因长度不能超过500个字符")
    private String changeReason;
    
    @Valid
    private List<CaseParticipantDTO> participantsToAdd;
    
    @Valid
    private List<Long> participantsToRemove;
    
    @Valid
    private List<CaseDocumentDTO> documentsToAdd;
    
    @Valid
    private List<Long> documentsToRemove;
    
    @Valid
    private List<CaseTeamDTO> teamMembersToAdd;
    
    @Valid
    private List<Long> teamMembersToRemove;
    
    private List<CaseReminderDTO> remindersToAdd;
    
    private List<Long> remindersToRemove;
    
    private Boolean needApproval = false;
    
    private String approvalFlow;
    
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
    private Integer version;
} 