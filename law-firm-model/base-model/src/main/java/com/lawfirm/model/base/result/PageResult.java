package com.lawfirm.model.base.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends Result<List<T>> {
    
    private long total;       // 总记录数
    private long size;        // 每页大小
    private long current;     // 当前页
    private long pages;       // 总页数

    public static <T> PageResult<T> ok(List<T> data, long total, long size, long current) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setSuccess(true);
        result.setMessage("操作成功");
        result.setData(data);
        result.setTotal(total);
        result.setSize(size);
        result.setCurrent(current);
        result.setPages((total + size - 1) / size);
        return result;
    }
} 