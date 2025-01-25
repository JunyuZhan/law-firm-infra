package com.lawfirm.cases.model.dto;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseStatusLogDTO {
    private Long id;
    private Long caseId;
    private CaseStatusEnum fromStatus;
    private CaseStatusEnum toStatus;
    private String operator;
    private String remark;
    private LocalDateTime createTime;
    private String createBy;
} 