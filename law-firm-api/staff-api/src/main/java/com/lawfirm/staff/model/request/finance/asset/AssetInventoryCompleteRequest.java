package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "资产盘点完成请求")
public class AssetInventoryCompleteRequest {
    
    @NotNull(message = "盘点ID不能为空")
    @Schema(description = "盘点ID")
    private Long id;
    
    @Schema(description = "备注")
    private String remark;
} 