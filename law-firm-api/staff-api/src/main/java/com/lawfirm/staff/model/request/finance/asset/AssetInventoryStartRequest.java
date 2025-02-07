package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "资产盘点开始请求")
public class AssetInventoryStartRequest {
    
    @NotNull(message = "盘点人不能为空")
    @Schema(description = "盘点人ID")
    private Long inventoryUserId;
    
    @Schema(description = "备注")
    private String remark;
} 