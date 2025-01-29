package com.lawfirm.model.cases.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseUpdateDTO extends BaseDTO {
    
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
    
    private String remark;
} 