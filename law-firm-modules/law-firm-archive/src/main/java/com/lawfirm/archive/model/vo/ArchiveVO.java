package com.lawfirm.archive.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案VO
 */
@Data
public class ArchiveVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 档案编号
     */
    private String archiveNumber;
    
    /**
     * 档案名称
     */
    private String archiveName;
    
    /**
     * 档案分类ID
     */
    private Long categoryId;
    
    /**
     * 档案分类名称
     */
    private String categoryName;
    
    /**
     * 档案描述
     */
    private String description;
    
    /**
     * 存储位置
     */
    private String location;
    
    /**
     * 档案状态
     */
    private String status;
    
    /**
     * 档案状态描述
     */
    private String statusDesc;
    
    /**
     * 保管期限
     */
    private String retentionPeriod;
    
    /**
     * 保管期限描述
     */
    private String retentionPeriodDesc;
    
    /**
     * 密级
     */
    private String securityLevel;
    
    /**
     * 密级描述
     */
    private String securityLevelDesc;
    
    /**
     * 归档时间
     */
    private LocalDateTime filingTime;
    
    /**
     * 归档人
     */
    private String filingPerson;
    
    /**
     * 当前借阅人
     */
    private String currentBorrower;
    
    /**
     * 预计归还时间
     */
    private LocalDateTime expectedReturnTime;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联客户ID
     */
    private Long clientId;
    
    /**
     * 电子文档路径
     */
    private String documentPath;
    
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