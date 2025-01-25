package com.lawfirm.common.data.query;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础查询类
 */
@Data
public class BaseQuery implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 创建时间范围（开始）
     */
    private LocalDateTime createTimeStart;
    
    /**
     * 创建时间范围（结束）
     */
    private LocalDateTime createTimeEnd;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新时间范围（开始）
     */
    private LocalDateTime updateTimeStart;
    
    /**
     * 更新时间范围（结束）
     */
    private LocalDateTime updateTimeEnd;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 删除标记（0：正常；1：删除）
     */
    private Integer delFlag;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页记录数
     */
    private Integer pageSize = 10;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方式（asc/desc）
     */
    private String orderType;
} 