package com.lawfirm.model.storage.entity.file;

import com.lawfirm.model.storage.entity.base.BaseStorage;
import com.lawfirm.model.storage.enums.FileTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件对象实体
 */
@Data
@Entity
@Table(name = "storage_file")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileObject extends BaseStorage {

    /**
     * 文件名
     */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    /**
     * 文件类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileTypeEnum fileType;

    /**
     * 文件扩展名
     */
    @Column(name = "extension")
    private String extension;

    /**
     * 文件MD5
     */
    @Column(name = "md5")
    private String md5;

    /**
     * 存储桶ID
     */
    @Column(name = "bucket_id")
    private Long bucketId;

    /**
     * 文件信息
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;
} 