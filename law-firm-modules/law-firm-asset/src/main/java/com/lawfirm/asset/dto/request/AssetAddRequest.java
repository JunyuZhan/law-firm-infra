package com.lawfirm.asset.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 资产添加请求
 */
@Data
public class AssetAddRequest {
    
    /**
     * 资产编号
     */
    @NotBlank(message = "资产编号不能为空")
    private String assetNo;
    
    /**
     * 资产名称
     */
    @NotBlank(message = "资产名称不能为空")
    private String assetName;
    
    /**
     * 资产类型
     */
    @NotNull(message = "资产类型不能为空")
    private Integer assetType;
    
    /**
     * 购入日期
     */
    @NotNull(message = "购入日期不能为空")
    private LocalDate purchaseDate;
    
    /**
     * 购入价格（单位：分）
     */
    @NotNull(message = "购入价格不能为空")
    private Long purchasePrice;
    
    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
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
