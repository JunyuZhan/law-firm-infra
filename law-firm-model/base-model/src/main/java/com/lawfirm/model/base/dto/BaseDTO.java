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
} 