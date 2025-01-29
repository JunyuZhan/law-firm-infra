package com.lawfirm.model.base.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础DTO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDTO extends com.lawfirm.common.data.dto.BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 排序字段
     */
    private String sort;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 操作人
     */
    private String operator;
} 