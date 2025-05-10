package com.lawfirm.model.archive.constant;

/**
 * 存储位置常量类
 */
public class StorageLocationConstant {
    
    /**
     * 本地存储
     */
    public static final String LOCAL_STORAGE = "LOCAL";
    
    /**
     * 阿里云OSS
     */
    public static final String ALIYUN_OSS = "ALIYUN_OSS";
    
    /**
     * 腾讯云COS
     */
    public static final String TENCENT_COS = "TENCENT_COS";
    
    /**
     * MinIO
     */
    public static final String MINIO = "MINIO";
    
    /**
     * 本地默认路径
     */
    public static final String LOCAL_DEFAULT_PATH = "/data/archive/files";
    
    /**
     * 临时文件路径
     */
    public static final String TEMP_PATH = "/data/archive/temp";
    
    /**
     * OSS默认Bucket
     */
    public static final String DEFAULT_BUCKET = "law-firm-archive";
    
    /**
     * 文件URL过期时间（秒）
     */
    public static final int URL_EXPIRATION = 3600;
} 