package com.lawfirm.archive.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 档案实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("archive")
public class Archive extends BaseEntity {
    
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
     * 档案描述
     */
    private String description;
    
    /**
     * 存储位置
     */
    private String location;
    
    /**
     * 档案状态（在库、借出、销毁）
     */
    private String status;
    
    /**
     * 保管期限
     */
    private String retentionPeriod;
    
    /**
     * 密级（公开、内部、保密、机密）
     */
    private String securityLevel;
    
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
} 