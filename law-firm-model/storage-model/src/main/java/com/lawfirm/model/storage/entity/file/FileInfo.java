package com.lawfirm.model.storage.entity.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件信息实体
 */
@Data
@TableName("storage_file_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileInfo extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;

    /**
     * 文件标签（逗号分隔）
     */
    @TableField("tags")
    private String tags;

    /**
     * 文件元数据（JSON格式）
     */
    @TableField("metadata")
    private String metadata;

    /**
     * 访问次数
     */
    @TableField("access_count")
    private Long accessCount = 0L;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Long downloadCount = 0L;

    /**
     * 最后访问时间
     */
    @TableField("last_access_time")
    private Long lastAccessTime;

    /**
     * 最后下载时间
     */
    @TableField("last_download_time")
    private Long lastDownloadTime;
} 