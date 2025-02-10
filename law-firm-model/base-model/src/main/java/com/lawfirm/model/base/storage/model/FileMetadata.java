package com.lawfirm.model.base.storage.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件元数据
 */
@Data
@Accessors(chain = true)
public class FileMetadata {
    
    /**
     * 文件ID
     */
    private String id;
    
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 文件类型
     */
    private String contentType;
    
    /**
     * 文件扩展名
     */
    private String extension;
    
    /**
     * 存储类型（minio/oss/mongodb）
     */
    private String storageType;
    
    /**
     * 存储路径
     */
    private String path;
    
    /**
     * 访问URL
     */
    private String url;
    
    /**
     * MD5值
     */
    private String md5;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private String businessId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 是否永久有效
     */
    private Boolean permanent = true;
    
    /**
     * 创建者ID
     */
    private Long creatorId;
    
    /**
     * 创建者名称
     */
    private String creatorName;
    
    /**
     * 租户ID
     */
    private Long tenantId;
} 