package com.lawfirm.staff.model.response.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "资产盘点响应")
public class AssetInventoryResponse {
    
    @Schema(description = "盘点ID")
    private Long id;
    
    @Schema(description = "盘点编号")
    private String code;
    
    @Schema(description = "盘点状态")
    private Integer status;
    
    @Schema(description = "盘点人ID")
    private Long inventoryUserId;
    
    @Schema(description = "盘点人姓名")
    private String inventoryUserName;
    
    @Schema(description = "盘点开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "盘点结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "盘点总数")
    private Integer totalCount;
    
    @Schema(description = "已盘点数")
    private Integer checkedCount;
    
    @Schema(description = "盘盈数")
    private Integer profitCount;
    
    @Schema(description = "盘亏数")
    private Integer lossCount;
    
    @Schema(description = "备注")
    private String remark;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 