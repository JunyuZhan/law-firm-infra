package com.lawfirm.cases.model.vo;

import com.lawfirm.cases.model.enums.CaseStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CaseDetailVO {
    private Long id;
    private String caseNo;
    private String caseName;
    private String caseType;
    private String client;
    private String clientContact;
    private String lawyer;
    private String description;
    private LocalDateTime acceptTime;
    private LocalDateTime expectedEndTime;
    private LocalDateTime actualEndTime;
    private BigDecimal caseAmount;
    private BigDecimal caseFee;
    private Boolean isMajor;
    private Boolean hasConflict;
    private CaseStatus status;
    private String remarks;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 扩展字段
    private String clientName;  // 委托人名称
    private String lawyerName;  // 律师名称
    private String statusName;  // 状态名称
    private Long documentCount; // 文档数量
    private Long progressCount; // 进度数量
    private String latestProgress; // 最新进度
} 