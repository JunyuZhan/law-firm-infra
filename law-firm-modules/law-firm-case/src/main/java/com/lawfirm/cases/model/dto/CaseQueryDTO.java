package com.lawfirm.cases.model.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseQueryDTO extends BaseDTO {
    private String caseNumber;
    private String caseName;
    private String description;
    private CaseTypeEnum caseType;
    private CaseStatusEnum caseStatus;
    private CaseProgressEnum progress;
    private CaseHandleTypeEnum handleType;
    private CaseDifficultyEnum difficulty;
    private CaseImportanceEnum importance;
    private CasePriorityEnum priority;
    private CaseFeeTypeEnum feeType;
    private CaseSourceEnum source;
    private String lawyer;
    private Long clientId;
    private Long lawFirmId;
    private Long branchId;
    private Long departmentId;
    private LocalDateTime filingTimeStart;
    private LocalDateTime filingTimeEnd;
    private LocalDateTime closingTimeStart;
    private LocalDateTime closingTimeEnd;
    private BigDecimal feeMin;
    private BigDecimal feeMax;
    private String courtName;
    private String judgeName;
    private String courtCaseNumber;
    private Boolean isMajor;
    private Boolean hasConflict;
} 