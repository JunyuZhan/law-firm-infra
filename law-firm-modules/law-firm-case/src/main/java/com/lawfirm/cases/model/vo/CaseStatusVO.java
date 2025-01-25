package com.lawfirm.cases.model.vo;

import com.lawfirm.cases.model.enums.CaseStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseStatusVO {
    private Long id;
    private Long caseId;
    private CaseStatus fromStatus;
    private CaseStatus toStatus;
    private String operator;
    private String reason;
    private LocalDateTime createTime;
    
    // 扩展字段
    private String fromStatusName;
    private String toStatusName;
    private String operatorName;
} 