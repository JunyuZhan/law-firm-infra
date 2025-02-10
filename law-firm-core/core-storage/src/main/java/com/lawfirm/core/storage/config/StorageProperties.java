package com.lawfirm.core.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 存储配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    
    /**
     * 存储类型（minio/oss/mongodb）
     */
    private String type = "minio";
    
    /**
     * 存储桶名称
     */
    private String bucketName = "lawfirm";
    
    /**
     * 默认URL过期时间（秒）
     */
    private Integer defaultUrlExpiry = 3600;
    
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
    
    /**
     * 默认存储桶
     */
    private String defaultBucket = "lawfirm";
    
    /**
     * 分片存储配置
     */
    private ChunkConfig chunk = new ChunkConfig();
    
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String region;
    private boolean secure = true;
    private int maxFileSize = 100 * 1024 * 1024; // 默认100MB
    private int chunkSize = 5 * 1024 * 1024; // 默认5MB
    private String tempDir = System.getProperty("java.io.tmpdir");
    
    @Data
    public static class MinioConfig {
        /**
         * 是否启用MinIO
         */
        private boolean enabled = false;
        
        /**
         * 服务地址
         */
        private String endpoint;
        
        /**
         * 访问密钥
         */
        private String accessKey;
        
        /**
         * 密钥
         */
        private String secretKey;
        
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
    
    @Data
    public static class ChunkConfig {
        /**
         * 分片存储目录
         */
        private String directory = "chunks";
        
        /**
         * 分片大小（字节）
         */
        private Long size = 5 * 1024 * 1024L;
        
        /**
         * 并发上传数
         */
        private Integer concurrent = 3;
    }
} 