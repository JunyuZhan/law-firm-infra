package com.lawfirm.model.finance.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoice_info")
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity implements StatusAware {

    @NotBlank(message = "发票编号不能为空")
    @Size(max = 50, message = "发票编号长度不能超过50个字符")
    @Column(nullable = false, length = 50, unique = true)
    private String invoiceNumber;  // 发票编号

    @NotNull(message = "发票类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvoiceTypeEnum invoiceType;  // 发票类型

    @NotNull(message = "发票状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvoiceStatusEnum invoiceStatus;

    @NotNull(message = "发票金额不能为空")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;  // 发票金额

    // 关联信息
    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID
    private Long clientId;   // 客户ID

    // 开票信息
    @NotBlank(message = "发票抬头不能为空")
    @Size(max = 200, message = "发票抬头长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String title;  // 发票抬头

    @Size(max = 500, message = "发票内容长度不能超过500个字符")
    @Column(length = 500)
    private String content;  // 发票内容

    private LocalDateTime issueTime;  // 开票时间

    @Size(max = 50, message = "开票人长度不能超过50个字符")
    @Column(length = 50)
    private String issuedBy;  // 开票人

    // 纳税人信息
    @Size(max = 50, message = "纳税人识别号长度不能超过50个字符")
    @Column(length = 50)
    private String taxpayerNumber;  // 纳税人识别号

    @Size(max = 200, message = "注册地址长度不能超过200个字符")
    @Column(length = 200)
    private String registeredAddress;  // 注册地址

    @Size(max = 50, message = "注册电话长度不能超过50个字符")
    @Column(length = 50)
    private String registeredPhone;  // 注册电话

    // 银行信息
    @Size(max = 100, message = "开户银行长度不能超过100个字符")
    @Column(length = 100)
    private String bankName;  // 开户银行

    @Size(max = 50, message = "银行账号长度不能超过50个字符")
    @Column(length = 50)
    private String bankAccount;  // 银行账号

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注信息

    @Override
    public StatusEnum getStatus() {
        if (invoiceStatus == null) {
            return StatusEnum.DISABLED;
        }
        switch (invoiceStatus) {
            case DRAFT:
            case PENDING:
                return StatusEnum.ENABLED;
            case ISSUED:
                return StatusEnum.ENABLED;
            case CANCELLED:
                return StatusEnum.DISABLED;
            case INVALID:
                return StatusEnum.DELETED;
            default:
                return StatusEnum.DISABLED;
        }
    }

    @Override
    public void setStatus(StatusEnum status) {
        if (status == null) {
            this.invoiceStatus = InvoiceStatusEnum.DRAFT;
            return;
        }
        switch (status) {
            case ENABLED:
                if (this.invoiceStatus == null || this.invoiceStatus == InvoiceStatusEnum.CANCELLED) {
                    this.invoiceStatus = InvoiceStatusEnum.DRAFT;
                }
                break;
            case DISABLED:
                this.invoiceStatus = InvoiceStatusEnum.CANCELLED;
                break;
            case DELETED:
                this.invoiceStatus = InvoiceStatusEnum.INVALID;
                break;
            case LOCKED:
            case EXPIRED:
                this.invoiceStatus = InvoiceStatusEnum.CANCELLED;
                break;
        }
    }
} 