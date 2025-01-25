package com.lawfirm.archive.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 档案借阅实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("archive_borrow")
public class ArchiveBorrow extends BaseEntity {
    
    /**
     * 档案ID
     */
    private Long archiveId;
    
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
     * 借阅状态（借出、已归还、超期）
     */
    private String status;
    
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
} 