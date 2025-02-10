package com.lawfirm.asset.dto.request;

import com.lawfirm.common.core.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetQueryRequest extends PageParam {
    
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
     * 状态
     */
    private Integer status;
    
    /**
     * 使用人ID
     */
    private Long userId;
}
