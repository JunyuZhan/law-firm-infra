package com.lawfirm.model.base.query;

import lombok.Data;
import java.io.Serializable;

@Data
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNum = 1;  // 当前页码
    private Integer pageSize = 10;  // 每页大小
    private String orderBy;  // 排序字段
    private Boolean asc = true;  // 是否升序

    private Long tenantId;  // 租户ID
    private Boolean enabled;  // 是否启用
    private String keyword;  // 关键字搜索
    
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
} 