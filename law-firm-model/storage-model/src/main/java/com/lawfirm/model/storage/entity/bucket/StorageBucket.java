package com.lawfirm.model.storage.entity.bucket;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.storage.entity.base.BaseStorage;
import com.lawfirm.model.storage.enums.BucketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 存储桶实体
 */
@Data
@TableName("storage_bucket")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StorageBucket extends BaseStorage {

    private static final long serialVersionUID = 1L;

    /**
     * 桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 桶类型
     */
    @TableField("bucket_type")
    private BucketTypeEnum bucketType;

    /**
     * 访问域名
     */
    @TableField("domain")
    private String domain;

    /**
     * 访问密钥
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * 密钥密文
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 桶配置JSON
     */
    @TableField("config")
    private String config;

    /**
     * 最大空间
     */
    @TableField("max_size")
    private Long maxSize;

    /**
     * 已用空间
     */
    @TableField("used_size")
    private Long usedSize = 0L;
    
    /**
     * 文件数量
     */
    @TableField("file_count")
    private Long fileCount = 0L;
} 