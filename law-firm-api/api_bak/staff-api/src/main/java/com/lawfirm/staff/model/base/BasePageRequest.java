package com.lawfirm.staff.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础分页请求
 */
@Data
public class BasePageRequest {
    
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小", example = "10")
    private Integer pageSize = 10;
    
    @Schema(description = "排序字段")
    private String orderBy;
    
    @Schema(description = "排序方式(asc/desc)")
    private String orderType;
} 