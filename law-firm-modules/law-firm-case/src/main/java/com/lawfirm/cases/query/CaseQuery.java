package com.lawfirm.cases.query;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseQuery {
    private String caseNo;
    private String caseName;
    private CaseTypeEnum caseType;
    private CaseStatusEnum caseStatus;
    private String lawyer;
    private Long clientId;
    private Long lawFirmId;
    private Long branchId;
    private Long departmentId;
    private LocalDateTime filingTimeStart;
    private LocalDateTime filingTimeEnd;
    private Boolean isMajor;
    private Boolean hasConflict;
} 