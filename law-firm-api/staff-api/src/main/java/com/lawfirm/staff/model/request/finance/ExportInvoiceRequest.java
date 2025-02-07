package com.lawfirm.staff.model.request.finance;

import com.lawfirm.staff.model.enums.ExportFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "导出发票请求")
public class ExportInvoiceRequest {

    @Schema(description = "发票抬头")
    private String title;

    @Schema(description = "发票类型")
    private Integer type;

    @Schema(description = "发票号码")
    private String invoiceNo;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "开始日期")
    private String startDate;

    @Schema(description = "结束日期")
    private String endDate;

    @Schema(description = "导出格式")
    private ExportFormat format = ExportFormat.EXCEL;
} 