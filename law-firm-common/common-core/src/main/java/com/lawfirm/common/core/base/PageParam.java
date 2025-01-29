package com.lawfirm.common.core.base;

import com.lawfirm.common.core.constant.CommonConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 */
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long current = CommonConstants.DEFAULT_CURRENT;

    /**
     * 每页显示记录数
     */
    private Long size = CommonConstants.DEFAULT_SIZE;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方向
     */
    private String order = CommonConstants.ASC;
} 