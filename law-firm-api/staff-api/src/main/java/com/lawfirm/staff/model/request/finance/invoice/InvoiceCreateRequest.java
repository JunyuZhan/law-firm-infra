package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票创建请求
 */
@Data
@Schema(description = "发票创建请求")
public class InvoiceCreateRequest {

    @Schema(description = "发票号码")
    @NotBlank(message = "发票号码不能为空")
    @Size(max = 50, message = "发票号码长度不能超过50个字符")
    private String invoiceNo;

    @Schema(description = "发票类型(1:增值税专用发票 2:增值税普通发票)")
    @NotNull(message = "发票类型不能为空")
    private Integer type;

    @Schema(description = "开票日期")
    @NotNull(message = "开票日期不能为空")
    private LocalDateTime invoiceDate;

    @Schema(description = "开票金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal amount;

    @Schema(description = "税率")
    @NotNull(message = "税率不能为空")
    private BigDecimal taxRate;

    @Schema(description = "税额")
    @NotNull(message = "税额不能为空")
    private BigDecimal taxAmount;

    @Schema(description = "开票单位")
    @NotBlank(message = "开票单位不能为空")
    private String company;

    @Schema(description = "纳税人识别号")
    @NotBlank(message = "纳税人识别号不能为空")
    private String taxNo;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "联系地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 