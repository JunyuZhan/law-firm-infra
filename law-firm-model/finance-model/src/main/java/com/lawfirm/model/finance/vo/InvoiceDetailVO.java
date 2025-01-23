package com.lawfirm.model.finance.vo;

import com.lawfirm.model.finance.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceDetailVO {

    private Long id;
    private String invoiceNumber;
    private InvoiceTypeEnum invoiceType;
    private InvoiceStatusEnum status;
    private BigDecimal amount;

    // 关联信息
    private LawFirmVO lawFirm;  // 律所信息
    private CaseVO caseInfo;    // 案件信息
    private ClientVO client;    // 客户信息

    // 开票信息
    private String title;       // 发票抬头
    private String content;     // 发票内容
    private LocalDateTime issueTime;  // 开票时间
    private String issuedBy;    // 开票人

    // 纳税人信息
    private String taxpayerNumber;     // 纳税人识别号
    private String registeredAddress;  // 注册地址
    private String registeredPhone;    // 注册电话

    // 银行信息
    private String bankName;     // 开户银行
    private String bankAccount;  // 银行账号

    // 时间信息
    private LocalDateTime createTime;   // 创建时间
    private String createBy;            // 创建人
    private LocalDateTime updateTime;   // 更新时间
    private String updateBy;            // 更新人

    private String remark;  // 备注信息

    @Data
    public static class LawFirmVO {
        private Long id;
        private String name;
    }

    @Data
    public static class CaseVO {
        private Long id;
        private String caseNumber;
        private String caseName;
    }

    @Data
    public static class ClientVO {
        private Long id;
        private String clientNumber;
        private String clientName;
        private String contactName;
        private String contactPhone;
    }
} 