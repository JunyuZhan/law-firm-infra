package com.lawfirm.model.storage.entity.bucket;

import com.lawfirm.model.storage.entity.base.BaseStorage;
import com.lawfirm.model.storage.enums.BucketTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 存储桶实体
 */
@Data
@Entity
@Table(name = "storage_bucket")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StorageBucket extends BaseStorage {

    /**
     * 桶名称
     */
    @Column(name = "bucket_name", nullable = false)
    private String bucketName;

    /**
     * 桶类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "bucket_type", nullable = false)
    private BucketTypeEnum bucketType;

    /**
     * 访问域名
     */
    @Column(name = "domain")
    private String domain;

    /**
     * 访问密钥
     */
    @Column(name = "access_key")
    private String accessKey;

    /**
     * 密钥密文
     */
    @Column(name = "secret_key")
    private String secretKey;

    /**
     * 配置信息（JSON格式）
     */
    @Column(name = "config", columnDefinition = "text")
    private String config;

    /**
     * 最大存储容量（字节）
     */
    @Column(name = "max_size")
    private Long maxSize;

    /**
     * 已用容量（字节）
     */
    @Column(name = "used_size")
    private Long usedSize = 0L;

    /**
     * 文件数量
     */
    @Column(name = "file_count")
    private Long fileCount = 0L;
} 