package com.lawfirm.model.base.query;

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
    private Long current = 1L;

    /**
     * 每页显示记录数
     */
    private Long size = 10L;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方向
     */
    private String order = "asc";
} 