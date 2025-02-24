package com.lawfirm.model.storage.dto.bucket;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.storage.enums.BucketTypeEnum;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 存储桶查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BucketQueryDTO extends BaseQuery {

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
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 