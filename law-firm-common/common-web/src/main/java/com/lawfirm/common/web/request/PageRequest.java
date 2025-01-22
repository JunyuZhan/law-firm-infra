package com.lawfirm.common.web.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页请求对象
 */
@Data
public class PageRequest {
    
    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为1")
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    @NotNull(message = "每页记录数不能为空")
    @Min(value = 1, message = "每页记录数最小值为1")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 是否升序
     */
    private Boolean isAsc = true;
} 