package com.lawfirm.staff.model.response.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "资产导入响应")
public class AssetImportResponse {
    
    @Schema(description = "成功导入数量")
    private Integer successCount;
    
    @Schema(description = "失败数量")
    private Integer failCount;
    
    @Schema(description = "更新数量")
    private Integer updateCount;
    
    @Schema(description = "失败原因")
    private String failReason;
} 