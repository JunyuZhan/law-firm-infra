package com.lawfirm.model.finance.query;

import com.lawfirm.model.base.query.PageQuery;
import com.lawfirm.model.finance.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceQuery extends PageQuery {

    private String invoiceNumber;  // 发票编号

    private InvoiceTypeEnum invoiceType;  // 发票类型

    private InvoiceStatusEnum status;  // 发票状态

    private BigDecimal amountMin;  // 最小金额
    private BigDecimal amountMax;  // 最大金额

    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID
    private Long clientId;   // 客户ID

    private String title;    // 发票抬头

    private LocalDateTime issueTimeStart;  // 开票时间起始
    private LocalDateTime issueTimeEnd;    // 开票时间结束

    private String issuedBy;  // 开票人

    private String taxpayerNumber;  // 纳税人识别号

    private LocalDateTime createTimeStart;  // 创建时间起始
    private LocalDateTime createTimeEnd;    // 创建时间结束
} 