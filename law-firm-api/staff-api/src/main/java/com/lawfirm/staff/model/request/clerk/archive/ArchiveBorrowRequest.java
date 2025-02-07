package com.lawfirm.staff.model.request.clerk.archive;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案借阅请求
 */
@Data
@Schema(description = "档案借阅请求")
public class ArchiveBorrowRequest {

    @Schema(description = "档案ID")
    @NotNull(message = "档案ID不能为空")
    private Long archiveId;

    @Schema(description = "借阅人")
    @NotBlank(message = "借阅人不能为空")
    private String borrower;

    @Schema(description = "预计归还时间")
    @NotNull(message = "预计归还时间不能为空")
    private LocalDateTime expectedReturnTime;

    @Schema(description = "借阅原因")
    @NotBlank(message = "借阅原因不能为空")
    private String borrowReason;

    @Schema(description = "备注")
    private String remark;
} 