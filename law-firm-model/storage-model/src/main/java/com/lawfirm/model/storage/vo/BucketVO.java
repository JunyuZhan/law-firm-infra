package com.lawfirm.model.storage.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.storage.enums.BucketTypeEnum;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 存储桶视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BucketVO extends BaseVO {

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 存储类型
     */
    private StorageTypeEnum storageType;

    /**
     * 桶类型
     */
    private BucketTypeEnum bucketType;

    /**
     * 访问域名
     */
    private String domain;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 配置信息
     */
    private String config;

    /**
     * 最大存储容量（字节）
     */
    private Long maxSize;

    /**
     * 已用容量（字节）
     */
    private Long usedSize;

    /**
     * 文件数量
     */
    private Long fileCount;

    /**
     * 状态（0：无效，1：有效）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
} 