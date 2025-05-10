package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 档案文件实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("archive_file")
public class ArchiveFile extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 档案标题
     */
    private String title;
    
    /**
     * 档案类型
     */
    @TableField("archive_type")
    private Integer archiveType;
    
    /**
     * 档案状态
     */
    @TableField("archive_status")
    private Integer archiveStatus;
    
    /**
     * 档案编号
     */
    @TableField("archive_no")
    private String archiveNo;
    
    /**
     * 相关业务ID（案件ID/合同ID等）
     */
    @TableField("business_id")
    private String businessId;
    
    /**
     * 业务类型（案件/合同等）
     */
    @TableField("business_type")
    private String businessType;
    
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    
    /**
     * 文件大小（KB）
     */
    @TableField("file_size")
    private Long fileSize;
    
    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;
    
    /**
     * 存储位置编码
     */
    @TableField("storage_location")
    private String storageLocation;
    
    /**
     * 归档时间
     */
    @TableField("archive_time")
    private Date archiveTime;
    
    /**
     * 归档人ID
     */
    @TableField("archive_user_id")
    private String archiveUserId;
    
    /**
     * 归档人姓名
     */
    @TableField("archive_user_name")
    private String archiveUserName;
    
    /**
     * 借阅状态（0-未借出，1-已借出）
     */
    @TableField("borrow_status")
    private Integer borrowStatus;
    
    /**
     * 借阅人ID
     */
    @TableField("borrower_id")
    private String borrowerId;
    
    /**
     * 借阅人姓名
     */
    @TableField("borrower_name")
    private String borrowerName;
    
    /**
     * 借阅时间
     */
    @TableField("borrow_time")
    private Date borrowTime;
    
    /**
     * 预计归还时间
     */
    @TableField("expected_return_time")
    private Date expectedReturnTime;
    
    /**
     * 实际归还时间
     */
    @TableField("actual_return_time")
    private Date actualReturnTime;
    
    /**
     * 同步状态（0-未同步，1-已同步）
     */
    @TableField("sync_status")
    private Integer syncStatus;
    
    /**
     * 同步时间
     */
    @TableField("sync_time")
    private Date syncTime;
    
    /**
     * 备注
     */
    private String remark;
} 