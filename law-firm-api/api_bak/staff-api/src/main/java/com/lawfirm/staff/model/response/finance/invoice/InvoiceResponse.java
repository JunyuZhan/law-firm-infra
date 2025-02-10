package com.lawfirm.staff.model.response.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票响应
 */
@Data
@Schema(description = "发票响应")
public class InvoiceResponse {

    @Schema(description = "发票ID")
    private Long id;

    @Schema(description = "发票号码")
    private String invoiceNo;

    @Schema(description = "发票类型(1:增值税专用发票 2:增值税普通发票)")
    private Integer type;

    @Schema(description = "发票类型名称")
    private String typeName;

    @Schema(description = "开票日期")
    private LocalDateTime invoiceDate;

    @Schema(description = "开票金额")
    private BigDecimal amount;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "税额")
    private BigDecimal taxAmount;

    @Schema(description = "开票单位")
    private String company;

    @Schema(description = "纳税人识别号")
    private String taxNo;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "联系地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "状态(0:待审核 1:已审核 2:已驳回)")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "审核人")
    private String approver;

    @Schema(description = "审核时间")
    private LocalDateTime approveTime;

    @Schema(description = "审核意见")
    private String approveRemark;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 