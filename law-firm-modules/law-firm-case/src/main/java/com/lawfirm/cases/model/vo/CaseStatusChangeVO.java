package com.lawfirm.cases.model.vo;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseStatusChangeVO {
    private Long id;
    private Long caseId;
    private CaseStatusEnum fromStatus;
    private CaseStatusEnum toStatus;
    private String operator;
    private String reason;
    private LocalDateTime createTime;
    
    // 扩展字段
    private String fromStatusName;
    private String toStatusName;
    private String operatorName;
} 
