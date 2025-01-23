package com.lawfirm.core.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 存储配置属性
 */
@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    
    /**
     * 存储类型（minio/oss/mongodb）
     */
    private String type = "minio";
    
    /**
     * MinIO 配置
     */
    private MinioConfig minio = new MinioConfig();
    
    /**
     * OSS 配置
     */
    private OssConfig oss = new OssConfig();
    
    /**
     * MongoDB 配置
     */
    private MongoConfig mongo = new MongoConfig();
    
    @Data
    public static class MinioConfig {
        /**
         * 服务端点
         */
        private String endpoint = "http://localhost:9000";
        
        /**
         * Access Key
         */
        private String accessKey = "minioadmin";
        
        /**
         * Secret Key
         */
        private String secretKey = "minioadmin";
        
        /**
         * 存储桶名称
         */
        private String bucketName = "lawfirm";
        
        /**
         * 默认URL过期时间（秒）
         */
        private long defaultUrlExpiry = 7200;
    }
    
    @Data
    public static class OssConfig {
        /**
         * 服务端点
         */
        private String endpoint;
        
        /**
         * Access Key ID
         */
        private String accessKeyId;
        
        /**
         * Access Key Secret
         */
        private String accessKeySecret;
        
        /**
         * 存储桶名称
         */
        private String bucketName;
        
        /**
         * 默认URL过期时间（秒）
         */
        private long defaultUrlExpiry = 7200;
    }
    
    @Data
    public static class MongoConfig {
        /**
         * 数据库名称
         */
        private String database = "lawfirm";
        
        /**
         * 集合名称
         */
        private String collection = "files";
        
        /**
         * 块大小（字节）
         */
        private int chunkSize = 358400;
    }
} 