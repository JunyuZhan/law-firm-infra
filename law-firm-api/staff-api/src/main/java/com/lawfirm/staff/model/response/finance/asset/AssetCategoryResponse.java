package com.lawfirm.staff.model.response.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "资产分类响应")
public class AssetCategoryResponse {
    
    @Schema(description = "分类ID")
    private Long id;
    
    @Schema(description = "分类名称")
    private String name;
    
    @Schema(description = "分类编码")
    private String code;
    
    @Schema(description = "父级ID")
    private Long parentId;
    
    @Schema(description = "排序")
    private Integer sort;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 