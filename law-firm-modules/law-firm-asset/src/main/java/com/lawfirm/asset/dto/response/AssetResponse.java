package com.lawfirm.asset.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 资产响应
 */
@Data
public class AssetResponse {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 资产编号
     */
    private String assetNo;
    
    /**
     * 资产名称
     */
    private String assetName;
    
    /**
     * 资产类型
     */
    private Integer assetType;
    
    /**
     * 资产类型名称
     */
    private String assetTypeName;
    
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
     * 状态名称
     */
    private String statusName;
    
    /**
     * 使用人ID
     */
    private Long userId;
    
    /**
     * 使用人姓名
     */
    private String userName;
    
    /**
     * 存放位置
     */
    private String location;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
