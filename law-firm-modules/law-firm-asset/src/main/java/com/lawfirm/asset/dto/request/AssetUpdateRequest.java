package com.lawfirm.asset.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 资产更新请求
 */
@Data
public class AssetUpdateRequest {
    
    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;
    
    /**
     * 资产名称
     */
    private String assetName;
    
    /**
     * 资产类型
     */
    private Integer assetType;
    
    /**
     * 购入日期
     */
    private LocalDate purchaseDate;
    
    /**
     * 购入价格（单位：分）
     */
    private Long purchasePrice;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 使用人ID
     */
    private Long userId;
    
    /**
     * 存放位置
     */
    private String location;
    
    /**
     * 备注
     */
    private String remark;
}
