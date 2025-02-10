package com.lawfirm.staff.model.request.finance.asset;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资产盘点分页查询请求")
public class AssetInventoryPageRequest extends PageQuery {
    
    @Schema(description = "盘点编号")
    private String code;
    
    @Schema(description = "盘点状态")
    private Integer status;
    
    @Schema(description = "盘点人ID")
    private Long inventoryById;
    
    @Schema(description = "盘点时间-起始")
    private String inventoryTimeBegin;
    
    @Schema(description = "盘点时间-结束")
    private String inventoryTimeEnd;
    
    @Schema(description = "创建时间-起始")
    private String createTimeBegin;
    
    @Schema(description = "创建时间-结束")
    private String createTimeEnd;
    
    @Schema(description = "关键字")
    private String keyword;
} 