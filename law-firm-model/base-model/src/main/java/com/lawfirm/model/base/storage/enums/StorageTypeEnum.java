package com.lawfirm.model.base.storage.enums;

import lombok.Getter;

/**
 * 存储类型枚举
 */
@Getter
public enum StorageTypeEnum {
    
    MINIO("minio", "MinIO对象存储"),
    OSS("oss", "阿里云OSS"),
    MONGODB("mongodb", "MongoDB存储");
    
    private final String code;
    private final String desc;
    
    StorageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 