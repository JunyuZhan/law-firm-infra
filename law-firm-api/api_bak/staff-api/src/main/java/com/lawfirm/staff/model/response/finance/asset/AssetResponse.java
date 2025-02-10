package com.lawfirm.staff.model.response.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "资产响应")
public class AssetResponse {

    @Schema(description = "资产ID")
    private Long id;

    @Schema(description = "资产名称")
    private String name;

    @Schema(description = "资产类型")
    private String type;

    @Schema(description = "资产金额")
    private BigDecimal amount;

    @Schema(description = "资产状态（0正常 1冻结）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 