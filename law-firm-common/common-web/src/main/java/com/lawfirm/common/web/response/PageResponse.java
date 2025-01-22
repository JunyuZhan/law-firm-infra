package com.lawfirm.common.web.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页响应对象
 */
@Getter
@Setter
@Accessors(chain = true)
public class PageResponse<T> {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有上一页
     */
    private Boolean hasPrev;

    public static <T> PageResponse<T> of(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        PageResponse<T> response = new PageResponse<>();
        response.list = list;
        response.total = total;
        response.pageNum = pageNum;
        response.pageSize = pageSize;
        
        // 计算总页数
        int pages = (int) Math.ceil((double) total / pageSize);
        response.pages = pages;
        
        // 设置是否有上一页/下一页
        response.hasPrev = pageNum > 1;
        response.hasNext = pageNum < pages;
        
        return response;
    }
} 