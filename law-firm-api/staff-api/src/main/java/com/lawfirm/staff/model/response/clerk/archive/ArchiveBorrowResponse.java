package com.lawfirm.staff.model.response.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案借阅响应
 */
@Data
@Schema(description = "档案借阅响应")
public class ArchiveBorrowResponse {

    @Schema(description = "借阅记录ID")
    private Long id;

    @Schema(description = "档案ID")
    private Long archiveId;

    @Schema(description = "档案名称")
    private String archiveName;

    @Schema(description = "借阅人")
    private String borrower;

    @Schema(description = "借阅时间")
    private LocalDateTime borrowTime;

    @Schema(description = "预计归还时间")
    private LocalDateTime expectedReturnTime;

    @Schema(description = "实际归还时间")
    private LocalDateTime actualReturnTime;

    @Schema(description = "借阅原因")
    private String borrowReason;

    @Schema(description = "借阅状态(1:借出 2:已归还)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 