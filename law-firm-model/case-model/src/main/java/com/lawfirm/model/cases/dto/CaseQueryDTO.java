package com.lawfirm.model.cases.dto;

import com.lawfirm.model.cases.enums.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseQueryDTO {
    private String keyword;
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
    private LocalDateTime filingTimeStart;
    private LocalDateTime filingTimeEnd;
    private String courtName;
    private String judgeName;
    private String courtCaseNumber;
    private Boolean isMajor;
    private Boolean hasConflict;
} 