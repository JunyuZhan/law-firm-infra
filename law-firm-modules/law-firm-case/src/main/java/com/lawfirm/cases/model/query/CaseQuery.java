package com.lawfirm.cases.model.query;

import com.lawfirm.cases.model.enums.CaseStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CaseQuery {
    private String keyword;  // 关键字搜索
    private String caseNo;   // 案件编号
    private String caseName; // 案件名称
    private String caseType; // 案件类型
    private String client;   // 委托人
    private String lawyer;   // 律师
    private CaseStatus status; // 案件状态
    private Boolean isMajor;   // 是否重大案件
    private Boolean hasConflict; // 是否有利益冲突
    private LocalDateTime acceptTimeStart; // 受理时间起始
    private LocalDateTime acceptTimeEnd;   // 受理时间结束
    private BigDecimal amountMin;  // 最小金额
    private BigDecimal amountMax;  // 最大金额
    private String sortField;      // 排序字段
    private String sortOrder;      // 排序方向
} 