package com.lawfirm.model.base.vo;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础VO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseVO extends BaseDTO {
    private static final long serialVersionUID = 1L;
} 