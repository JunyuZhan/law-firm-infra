package com.lawfirm.asset.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("asset")
public class Asset extends BaseEntity {
    
    /**
     * 资产编号
     */
    private String assetNo;
    
    /**
     * 资产名称
     */
    private String assetName;
    
    /**
     * 资产类型（1:办公设备、2:电子设备、3:家具、4:其他）
     */
    private Integer assetType;
    
    /**
     * 购入日期
     */
    private java.time.LocalDate purchaseDate;
    
    /**
     * 购入价格（单位：分）
     */
    private Long purchasePrice;
    
    /**
     * 状态（1:在用、2:闲置、3:维修、4:报废）
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
