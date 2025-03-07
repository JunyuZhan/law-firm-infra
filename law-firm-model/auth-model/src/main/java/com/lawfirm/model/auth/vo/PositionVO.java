package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 职位视图对象
 */
@Data
public class PositionVO {
    
    /**
     * 职位ID
     */
    private Long id;
    
    /**
     * 职位名称
     */
    private String name;
    
    /**
     * 职位编码
     */
    private String code;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 职位状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
} 