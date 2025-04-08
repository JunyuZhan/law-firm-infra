package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务附件实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_task_attachment")
public class WorkTaskAttachment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableField("task_id")
    private Long taskId;
    
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    
    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;
    
    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;
    
    /**
     * 文件MD5
     */
    @TableField("file_md5")
    private String fileMd5;
    
    /**
     * 上传者ID
     */
    @TableField("uploader_id")
    private Long uploaderId;
    
    /**
     * 上传者姓名
     */
    @TableField("uploader_name")
    private String uploaderName;
    
    /**
     * 上传者头像
     */
    @TableField("uploader_avatar")
    private String uploaderAvatar;
    
    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    private String fileSuffix;
    
    /**
     * 预览地址
     */
    @TableField("preview_url")
    private String previewUrl;
    
    /**
     * 下载地址
     */
    @TableField("download_url")
    private String downloadUrl;
    
    /**
     * 下载次数
     */
    @TableField("download_count")
    private Integer downloadCount;
    
    /**
     * 是否可预览
     */
    @TableField("previewable")
    private Boolean previewable;
    
    /**
     * 文件图标
     */
    @TableField("file_icon")
    private String fileIcon;
    
    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
} 