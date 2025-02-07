package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "资产导出请求")
public class AssetExportRequest {
    
    @Schema(description = "资产编号")
    private String code;
    
    @Schema(description = "资产名称")
    private String name;
    
    @Schema(description = "资产类型")
    private Integer type;
    
    @Schema(description = "资产状态")
    private Integer status;
    
    @Schema(description = "分类ID")
    private Long categoryId;
    
    @Schema(description = "位置ID")
    private Long locationId;
    
    @Schema(description = "开始时间")
    private String startTime;
    
    @Schema(description = "结束时间")
    private String endTime;
} 