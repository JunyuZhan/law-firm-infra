package com.lawfirm.common.data.query;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询基类
 */
@Data
@Accessors(chain = true)
public class PageQuery {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方向（asc/desc）
     */
    private String orderDirection;
} 