package com.lawfirm.core.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 存储配置属性
 */
@Data
@Component("coreStorageProperties")
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    
    /**
     * 默认存储类型
     */
    private String defaultType = "LOCAL";
    
    /**
     * 默认存储桶ID
     */
    private Long defaultBucketId = 1L;
    
    /**
     * 路径配置
     */
    private PathConfig path = new PathConfig();
    
    /**
     * 上传配置
     */
    private UploadConfig upload = new UploadConfig();
    
    /**
     * 本地存储配置
     */
    private LocalConfig local = new LocalConfig();
    
    /**
     * MinIO存储配置
     */
    private MinioConfig minio = new MinioConfig();
    
    /**
     * 阿里云OSS存储配置
     */
    private AliyunOssConfig aliyunOss = new AliyunOssConfig();
    
    /**
     * 腾讯云COS存储配置
     */
    private TencentCosConfig tencentCos = new TencentCosConfig();
    
    /**
     * 路径配置
     */
    @Data
    public static class PathConfig {
        /**
         * 本地存储根路径
         */
        private String baseDir = "/data/files";
        
        /**
         * 临时目录
         */
        private String tempDir = "/data/temp";
        
        /**
         * URL前缀
         */
        private String urlPrefix = "http://localhost:8080/api/files";
    }
    
    /**
     * 上传配置
     */
    @Data
    public static class UploadConfig {
        /**
         * 最大文件大小 (100MB)
         */
        private long maxSize = 104857600;
        
        /**
         * 分片大小 (5MB)
         */
        private int chunkSize = 5242880;
        
        /**
         * 允许的文件类型
         */
        private String allowedTypes = "*";
        
        /**
         * 禁止的文件类型
         */
        private String deniedTypes = "exe,bat,sh,dll";
    }
    
    /**
     * 本地存储配置
     */
    @Data
    public static class LocalConfig {
        /**
         * 是否启用
         */
        private boolean enabled = true;
        
        /**
         * 存储根路径
         */
        private String basePath = "/data/files";
        
        /**
         * URL前缀
         */
        private String urlPrefix = "http://localhost:8080/api/files";
        
        /**
         * 获取临时文件存储路径
         * @return 临时文件存储路径
         */
        public String getTempPath() {
            return basePath + "/temp";
        }
    }
    
    /**
     * MinIO存储配置
     */
    @Data
    public static class MinioConfig {
        /**
         * 是否启用
         */
        private boolean enabled = true;
        
        /**
         * 服务端点
         */
        private String endpoint = "http://minio.example.com";
        
        /**
         * 端口号
         */
        private int port = 9000;
        
        /**
         * 访问密钥
         */
        private String accessKey = "minioadmin";
        
        /**
         * 访问密钥
         */
        private String secretKey = "minioadmin";
        
        /**
         * 是否使用SSL
         */
        private boolean useSsl = false;
    }
    
    /**
     * 阿里云OSS存储配置
     */
    @Data
    public static class AliyunOssConfig {
        /**
         * 是否启用
         */
        private boolean enabled = false;
        
        /**
         * 服务端点
         */
        private String endpoint = "oss-cn-beijing.aliyuncs.com";
        
        /**
         * 访问密钥
         */
        private String accessKey;
        
        /**
         * 访问密钥
         */
        private String secretKey;
    }
    
    /**
     * 腾讯云COS存储配置
     */
    @Data
    public static class TencentCosConfig {
        /**
         * 是否启用
         */
        private boolean enabled = false;
        
        /**
         * 区域
         */
        private String region = "ap-beijing";
        
        /**
         * 应用ID
         */
        private String appId;
        
        /**
         * 密钥ID
         */
        private String secretId;
        
        /**
         * 密钥
         */
        private String secretKey;
    }
} 