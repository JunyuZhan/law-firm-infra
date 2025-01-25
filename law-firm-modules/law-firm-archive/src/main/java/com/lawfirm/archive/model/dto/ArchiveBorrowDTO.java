package com.lawfirm.archive.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案借阅DTO
 */
@Data
public class ArchiveBorrowDTO {
    
    /**
     * 档案ID
     */
    @NotNull(message = "档案ID不能为空")
    private Long archiveId;
    
    /**
     * 借阅人
     */
    @NotBlank(message = "借阅人不能为空")
    private String borrower;
    
    /**
     * 预计归还时间
     */
    @NotNull(message = "预计归还时间不能为空")
    private LocalDateTime expectedReturnTime;
    
    /**
     * 借阅原因
     */
    @NotBlank(message = "借阅原因不能为空")
    private String borrowReason;
    
    /**
     * 备注
     */
    private String remarks;
} 