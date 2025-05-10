package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 同步配置实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("archive_sync_config")
public class SyncConfig extends ModelBaseEntity {

    private static final long serialVersionUID = 2L;

    /**
     * 系统编码
     */
    @TableField("system_code")
    private String systemCode;

    /**
     * 系统名称
     */
    @TableField("system_name")
    private String systemName;

    /**
     * 同步接口地址
     */
    @TableField("sync_url")
    private String syncUrl;

    /**
     * 认证令牌
     */
    @TableField("auth_token")
    private String authToken;

    /**
     * 同步方式（1-手动，2-自动）
     */
    @TableField("sync_mode")
    private Integer syncMode;

    /**
     * 同步频率（分钟）
     */
    @TableField("sync_frequency")
    private Integer syncFrequency;

    /**
     * 同步批次大小
     */
    @TableField("batch_size")
    private Integer batchSize;

    /**
     * 最大批量数量
     */
    @TableField("max_batch_size")
    private Integer maxBatchSize;

    /**
     * 连接超时时间（秒）
     */
    @TableField("timeout")
    private Integer timeout;

    /**
     * 最大重试次数
     */
    @TableField("max_retry")
    private Integer maxRetry;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    @TableField("enabled")
    private Integer enabled;
}