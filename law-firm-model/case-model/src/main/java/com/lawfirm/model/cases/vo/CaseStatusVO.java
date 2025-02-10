package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseStatusVO {

    private Long id;
    
    private Long caseId;
    
    private CaseStatusEnum fromStatus;
    
    private CaseStatusEnum toStatus;
    
    private String operator;
    
    private String operatorDepartment;
    
    private String reason;
    
    private LocalDateTime operateTime;
    
    private String operatorIp;
    
    private String operatorLocation;
    
    private String relatedDocuments;
    
    private Boolean needNotify;
    
    private String notifyTo;
    
    private LocalDateTime notifyTime;
    
    private String changeType;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 