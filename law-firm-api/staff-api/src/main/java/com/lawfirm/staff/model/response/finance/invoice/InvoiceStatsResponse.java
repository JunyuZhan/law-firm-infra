package com.lawfirm.staff.model.response.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "发票统计响应")
public class InvoiceStatsResponse {

    @Schema(description = "发票总数")
    private Long totalCount;

    @Schema(description = "发票总金额")
    private BigDecimal totalAmount;

    @Schema(description = "待开具数量")
    private Long pendingCount;

    @Schema(description = "已开具数量")
    private Long issuedCount;

    @Schema(description = "已作废数量")
    private Long voidedCount;

    @Schema(description = "待开具金额")
    private BigDecimal pendingAmount;

    @Schema(description = "已开具金额")
    private BigDecimal issuedAmount;

    @Schema(description = "已作废金额")
    private BigDecimal voidedAmount;
} 