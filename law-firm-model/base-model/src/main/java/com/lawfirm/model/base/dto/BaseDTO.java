package com.lawfirm.model.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO基类
 */
@Data
@Accessors(chain = true)
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页记录数
     */
    private Integer pageSize = 10;
    
    /**
     * 关键字，用于模糊查询
     */
    private String keyword;
    
    /**
     * 排序字段
     */
    private String orderField;
    
    /**
     * 排序方向（asc/desc）
     */
    private String orderDirection;
} 