package com.lawfirm.model.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储类型枚举
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {

    LOCAL("LOCAL", "本地存储"),
    MINIO("MINIO", "MinIO对象存储"),
    ALIYUN_OSS("ALIYUN_OSS", "阿里云OSS"),
    TENCENT_COS("TENCENT_COS", "腾讯云COS"),
    AWS_S3("AWS_S3", "亚马逊S3存储");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 