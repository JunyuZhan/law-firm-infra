package com.lawfirm.model.finance.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_invoice")
public class Invoice extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "发票编号不能为空")
    @Size(max = 50, message = "发票编号长度不能超过50个字符")
    @TableField("invoice_number")
    private String invoiceNumber;  // 发票编号

    @NotNull(message = "发票类型不能为空")
    @TableField("invoice_type")
    private InvoiceTypeEnum invoiceType;  // 发票类型

    @NotNull(message = "发票状态不能为空")
    @TableField("invoice_status")
    private InvoiceStatusEnum invoiceStatus;

    @NotNull(message = "发票金额不能为空")
    @TableField("amount")
    private BigDecimal amount;  // 发票金额

    // 关联信息
    @TableField("law_firm_id")
    private Long lawFirmId;  // 律所ID
    
    @TableField("case_id")
    private Long caseId;     // 案件ID
    
    @TableField("client_id")
    private Long clientId;   // 客户ID

    // 开票信息
    @NotBlank(message = "发票抬头不能为空")
    @Size(max = 200, message = "发票抬头长度不能超过200个字符")
    @TableField("title")
    private String title;  // 发票抬头

    @Size(max = 500, message = "发票内容长度不能超过500个字符")
    @TableField("content")
    private String content;  // 发票内容

    @TableField("issue_time")
    private LocalDateTime issueTime;  // 开票时间

    @Size(max = 50, message = "开票人长度不能超过50个字符")
    @TableField("issued_by")
    private String issuedBy;  // 开票人

    // 纳税人信息
    @Size(max = 50, message = "纳税人识别号长度不能超过50个字符")
    @TableField("taxpayer_number")
    private String taxpayerNumber;  // 纳税人识别号

    @Size(max = 200, message = "注册地址长度不能超过200个字符")
    @TableField("registered_address")
    private String registeredAddress;  // 注册地址

    @Size(max = 50, message = "注册电话长度不能超过50个字符")
    @TableField("registered_phone")
    private String registeredPhone;  // 注册电话

    // 银行信息
    @Size(max = 100, message = "开户银行长度不能超过100个字符")
    @TableField("bank_name")
    private String bankName;  // 开户银行

    @Size(max = 50, message = "银行账号长度不能超过50个字符")
    @TableField("bank_account")
    private String bankAccount;  // 银行账号

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @TableField("remark")
    private String remark;  // 备注信息
}