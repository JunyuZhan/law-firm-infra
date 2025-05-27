package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档存储类型枚举
 */
@Getter
public enum DocumentStorageTypeEnum {

    /**
     * 本地存储
     */
    LOCAL("LOCAL", "本地存储"),

    /**
     * MinIO对象存储
     */
    MINIO("MINIO", "MinIO存储"),

    /**
     * 阿里云OSS
     */
    ALIYUN_OSS("ALIYUN_OSS", "阿里云OSS"),

    /**
     * 腾讯云COS
     */
    TENCENT_COS("TENCENT_COS", "腾讯云COS"),

    /**
     * 七牛云存储
     */
    QINIU("QINIU", "七牛云存储"),

    /**
     * 数据库存储
     */
    DATABASE("DATABASE", "数据库存储");

    /**
     * 存储类型编码
     */
    @EnumValue
    @JsonValue
    private final String code;

    /**
     * 存储类型名称
     */
    private final String name;

    DocumentStorageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据编码获取枚举
     */
    public static DocumentStorageTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (DocumentStorageTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 