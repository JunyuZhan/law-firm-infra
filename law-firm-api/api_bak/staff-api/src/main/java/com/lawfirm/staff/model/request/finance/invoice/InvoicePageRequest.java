package com.lawfirm.staff.model.request.finance.invoice;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "发票分页查询请求")
public class InvoicePageRequest extends PageQuery {

    @Schema(description = "发票编号")
    private String code;

    @Schema(description = "发票类型")
    private Integer type;

    @Schema(description = "发票状态")
    private Integer status;

    @Schema(description = "开票时间-起始")
    private String invoiceTimeBegin;

    @Schema(description = "开票时间-结束")
    private String invoiceTimeEnd;

    @Schema(description = "创建时间-起始")
    private String createTimeBegin;

    @Schema(description = "创建时间-结束")
    private String createTimeEnd;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "最小金额")
    private BigDecimal minAmount;

    @Schema(description = "最大金额")
    private BigDecimal maxAmount;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;
} 