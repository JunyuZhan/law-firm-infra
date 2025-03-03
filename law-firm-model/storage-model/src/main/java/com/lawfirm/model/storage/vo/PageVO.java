package com.lawfirm.model.storage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果视图对象
 * 
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
public class PageVO<T> {
    
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
    
    /**
     * 构造分页视图对象
     * 
     * @param list 数据列表
     * @param total 总记录数
     */
    public PageVO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
    
    /**
     * 构造分页视图对象
     * 
     * @param list 数据列表
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     */
    public PageVO(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
} 