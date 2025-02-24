package com.lawfirm.model.document.storage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 存储配置实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_storage_config")
public class StorageConfig extends ModelBaseEntity {

    /**
     * 存储类型（LOCAL、OSS、COS等）
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 存储区域
     */
    @TableField("region")
    private String region;

    /**
     * 访问密钥ID
     */
    @TableField("access_key_id")
    private String accessKeyId;

    /**
     * 访问密钥密码
     */
    @TableField("access_key_secret")
    private String accessKeySecret;

    /**
     * 存储桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 存储桶域名
     */
    @TableField("bucket_domain")
    private String bucketDomain;

    /**
     * 基础路径
     */
    @TableField("base_path")
    private String basePath;

    /**
     * 是否默认配置
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 是否启用
     */
    @TableField("is_enabled")
    private Boolean isEnabled;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 