package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;

@Data
public class CaseStatusChangeVO {
    private Long caseId;
    private CaseStatusEnum fromStatus;
    private CaseStatusEnum toStatus;
    private String operator;
    private String reason;
} 