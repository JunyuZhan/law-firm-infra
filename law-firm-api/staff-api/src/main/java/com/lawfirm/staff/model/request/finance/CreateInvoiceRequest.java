package com.lawfirm.staff.model.request.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "创建发票请求")
public class CreateInvoiceRequest {

    @Schema(description = "发票抬头")
    private String title;

    @Schema(description = "发票类型")
    private Integer type;

    @Schema(description = "发票金额")
    private BigDecimal amount;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "税额")
    private BigDecimal taxAmount;

    @Schema(description = "开票日期")
    private LocalDateTime issueDate;

    @Schema(description = "发票号码")
    private String invoiceNo;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "备注")
    private String remark;
} 