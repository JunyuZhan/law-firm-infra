package com.lawfirm.common.data.query;

import lombok.Data;

/**
 * 分页查询基类
 */
@Data
public class PageQuery {
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式（asc/desc）
     */
    private String orderDirection = "asc";

    /**
     * 是否查询总数
     */
    private Boolean searchCount = true;
}