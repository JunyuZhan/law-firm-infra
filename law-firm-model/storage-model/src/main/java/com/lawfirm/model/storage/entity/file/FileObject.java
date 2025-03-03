package com.lawfirm.model.storage.entity.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.storage.entity.base.BaseStorage;
import com.lawfirm.model.storage.enums.FileTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件对象实体
 */
@Data
@TableName("storage_file")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileObject extends BaseStorage {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private FileTypeEnum fileType;

    /**
     * 文件扩展名
     */
    @TableField("extension")
    private String extension;

    /**
     * 文件MD5
     */
    @TableField("md5")
    private String md5;

    /**
     * 存储桶ID
     */
    @TableField("bucket_id")
    private Long bucketId;
    
    /**
     * 存储路径
     */
    @TableField("storage_path")
    private String storagePath;
    
    /**
     * 存储大小（字节）
     */
    @TableField("storage_size")
    private Long storageSize;
    
    /**
     * 文件信息
     */
    @TableField(exist = false)
    private FileInfo fileInfo;
    
    /**
     * UUID标识符
     */
    @TableField("uuid")
    private String uuid;
    
    /**
     * 上传时间
     */
    @TableField("upload_time")
    private Long uploadTime;
    
    /**
     * 内容类型
     */
    @TableField("content_type")
    private String contentType;
    
    /**
     * 元数据信息（JSON格式）
     */
    @TableField("metadata")
    private String metadata;

    public String getStoragePath() {
        return this.storagePath;
    }
    
    public Long getStorageSize() {
        return this.storageSize;
    }
    
    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    // 以下为FileInfo的代理方法
    public void setDescription(String description) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setDescription(description);
    }
    
    public String getDescription() {
        return this.fileInfo != null ? this.fileInfo.getDescription() : null;
    }
    
    public void setTags(String tags) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setTags(tags);
    }
    
    public String getTags() {
        return this.fileInfo != null ? this.fileInfo.getTags() : null;
    }
    
    public void setAccessCount(Long accessCount) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setAccessCount(accessCount);
    }
    
    public Long getAccessCount() {
        return this.fileInfo != null ? this.fileInfo.getAccessCount() : 0L;
    }
    
    public void setDownloadCount(Long downloadCount) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setDownloadCount(downloadCount);
    }
    
    public Long getDownloadCount() {
        return this.fileInfo != null ? this.fileInfo.getDownloadCount() : 0L;
    }
    
    public void setLastAccessTime(Long lastAccessTime) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setLastAccessTime(lastAccessTime);
    }
    
    public Long getLastAccessTime() {
        return this.fileInfo != null ? this.fileInfo.getLastAccessTime() : null;
    }
    
    public void setLastDownloadTime(Long lastDownloadTime) {
        if (this.fileInfo == null) {
            this.fileInfo = new FileInfo();
        }
        this.fileInfo.setLastDownloadTime(lastDownloadTime);
    }
    
    public Long getLastDownloadTime() {
        return this.fileInfo != null ? this.fileInfo.getLastDownloadTime() : null;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    public String getMetadata() {
        return this.metadata;
    }
    
    // 兼容方法 - 将fileSize映射到storageSize
    public void setFileSize(long fileSize) {
        this.storageSize = fileSize;
    }
    
    public Long getFileSize() {
        return this.storageSize;
    }
} 