package com.lawfirm.staff.model.request.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "发票分页查询请求")
public class InvoicePageRequest {

    @Schema(description = "页码")
    private Integer pageNum = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;

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
} 