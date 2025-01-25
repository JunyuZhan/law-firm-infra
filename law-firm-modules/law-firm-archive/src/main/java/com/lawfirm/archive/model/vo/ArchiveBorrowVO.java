package com.lawfirm.archive.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案借阅VO
 */
@Data
public class ArchiveBorrowVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 档案ID
     */
    private Long archiveId;
    
    /**
     * 档案编号
     */
    private String archiveNumber;
    
    /**
     * 档案名称
     */
    private String archiveName;
    
    /**
     * 借阅人
     */
    private String borrower;
    
    /**
     * 借阅时间
     */
    private LocalDateTime borrowTime;
    
    /**
     * 预计归还时间
     */
    private LocalDateTime expectedReturnTime;
    
    /**
     * 实际归还时间
     */
    private LocalDateTime actualReturnTime;
    
    /**
     * 借阅原因
     */
    private String borrowReason;
    
    /**
     * 借阅状态
     */
    private String status;
    
    /**
     * 借阅状态描述
     */
    private String statusDesc;
    
    /**
     * 审批人
     */
    private String approver;
    
    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;
    
    /**
     * 审批意见
     */
    private String approvalOpinion;
    
    /**
     * 备注
     */
    private String remarks;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 