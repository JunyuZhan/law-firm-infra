package com.lawfirm.model.auth.dto.position;

import lombok.Data;

/**
 * 职位查询DTO
 */
@Data
public class PositionQueryDTO {
    
    /**
     * 职位名称
     */
    private String name;
    
    /**
     * 职位编码
     */
    private String code;
    
    /**
     * 职位状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页数量
     */
    private Integer pageSize = 10;
} 