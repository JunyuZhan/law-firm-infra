package com.lawfirm.model.base.storage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.lawfirm.model.base.entity.ModelBaseEntity;

import java.time.LocalDateTime;

/**
 * 文件元数据实体
 */
@Getter
@Setter
@Entity
@Table(name = "base_file_metadata")
@Accessors(chain = true)
public class FileMetadataEntity extends ModelBaseEntity<FileMetadataEntity> {
    
    /**
     * 文件唯一标识
     */
    private String fileId;
    
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 文件类型
     */
    private String contentType;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 存储路径
     */
    private String path;
    
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
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新人
     */
    private String updateBy;
} 