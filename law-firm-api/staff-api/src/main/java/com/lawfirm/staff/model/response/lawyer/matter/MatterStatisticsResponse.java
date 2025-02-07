package com.lawfirm.staff.model.response.lawyer.matter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "案件统计响应")
public class MatterStatisticsResponse {
    
    @Schema(description = "总案件数")
    private Integer totalCount;
    
    @Schema(description = "进行中案件数")
    private Integer processingCount;
    
    @Schema(description = "已结案案件数")
    private Integer closedCount;
    
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    
    @Schema(description = "本月新增案件数")
    private Integer monthlyNewCount;
    
    @Schema(description = "本月结案数")
    private Integer monthlyClosedCount;
    
    @Schema(description = "本月案件金额")
    private BigDecimal monthlyAmount;
} 