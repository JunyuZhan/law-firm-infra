package com.lawfirm.model.storage.entity.file;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件信息实体
 */
@Data
@Entity
@Table(name = "storage_file_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileInfo extends ModelBaseEntity {

    /**
     * 文件描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 文件标签（逗号分隔）
     */
    @Column(name = "tags")
    private String tags;

    /**
     * 文件元数据（JSON格式）
     */
    @Column(name = "metadata", columnDefinition = "text")
    private String metadata;

    /**
     * 访问次数
     */
    @Column(name = "access_count")
    private Long accessCount = 0L;

    /**
     * 下载次数
     */
    @Column(name = "download_count")
    private Long downloadCount = 0L;

    /**
     * 最后访问时间
     */
    @Column(name = "last_access_time")
    private Long lastAccessTime;

    /**
     * 最后下载时间
     */
    @Column(name = "last_download_time")
    private Long lastDownloadTime;
} 