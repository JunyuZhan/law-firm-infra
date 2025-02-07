package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "资产报废请求")
public class AssetScrapRequest {
    
    @NotNull(message = "资产ID不能为空")
    @Schema(description = "资产ID")
    private Long assetId;
    
    @NotBlank(message = "报废原因不能为空")
    @Schema(description = "报废原因")
    private String reason;
    
    @Schema(description = "备注")
    private String remark;
} 