package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPostVO extends BaseVO {
    
    /**
     * 岗位编码
     */
    private String code;
    
    /**
     * 岗位名称
     */
    private String name;
    
    /**
     * 状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 备注
     */
    private String remark;
} 