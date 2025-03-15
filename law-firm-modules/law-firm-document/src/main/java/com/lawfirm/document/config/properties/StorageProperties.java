package com.lawfirm.document.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文档存储配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "lawfirm.document.storage")
public class StorageProperties {
    
    /**
     * 存储类型：local/oss/s3
     */
    private String type = "local";
    
    /**
     * 本地存储路径
     */
    private String localPath = "data/documents";
    
    /**
     * OSS配置
     */
    private OssProperties oss = new OssProperties();
    
    /**
     * S3配置
     */
    private S3Properties s3 = new S3Properties();
    
    /**
     * 文件上传大小限制（MB）
     */
    private int maxFileSize = 100;
    
    /**
     * 允许的文件类型
     */
    private String[] allowedFileTypes = {"doc", "docx", "pdf", "xls", "xlsx", "ppt", "pptx"};
    
    /**
     * 是否启用文件去重
     */
    private boolean enableDeduplication = true;
    
    /**
     * 是否启用文件压缩
     */
    private boolean enableCompression = true;
    
    /**
     * 压缩阈值（MB）
     */
    private int compressionThreshold = 10;
    
    /**
     * 是否启用文件备份
     */
    private boolean enableBackup = true;
    
    /**
     * 备份路径
     */
    private String backupPath = "data/backup";
    
    /**
     * 备份保留天数
     */
    private int backupRetentionDays = 30;

    @Data
    public static class OssProperties {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private String region;
    }

    @Data
    public static class S3Properties {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private String region;
    }
}
