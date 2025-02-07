package com.lawfirm.staff.model.request.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 档案借阅分页请求
 */
@Data
@Schema(description = "档案借阅分页请求")
public class ArchiveBorrowPageRequest {

    @Schema(description = "页码")
    private Integer pageNum = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "借阅状态(1:借出 2:已归还)")
    private Integer status;

    @Schema(description = "借阅人")
    private String borrower;

    @Schema(description = "排序字段")
    private String orderBy;

    @Schema(description = "排序方式(asc,desc)")
    private String orderType;
} 