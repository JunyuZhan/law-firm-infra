package com.lawfirm.model.base.dto;

import lombok.Data;
import java.util.List;

/**
 * 通用分页数据传输对象
 */
@Data
public class PageDTO<T> {
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    
    /**
     * 总记录数
     */
    private Long total = 0L;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    public PageDTO() {}
    
    public PageDTO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
    
    public PageDTO(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
} 