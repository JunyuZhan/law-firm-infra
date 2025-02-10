package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "发票导出请求")
public class InvoiceExportRequest {
    
    @Schema(description = "发票编号")
    private String code;
    
    @Schema(description = "发票类型")
    private Integer type;
    
    @Schema(description = "发票状态")
    private Integer status;
    
    @Schema(description = "开票人ID")
    private Long drawerId;
    
    @Schema(description = "开始时间")
    private String startTime;
    
    @Schema(description = "结束时间")
    private String endTime;
} 