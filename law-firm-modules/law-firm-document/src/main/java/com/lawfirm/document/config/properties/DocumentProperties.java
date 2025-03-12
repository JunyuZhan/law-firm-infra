package com.lawfirm.document.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文档模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "lawfirm.document")
public class DocumentProperties {
    
    /**
     * 存储配置
     */
    private Storage storage = new Storage();
    
    /**
     * 缓存配置
     */
    private Cache cache = new Cache();
    
    /**
     * 安全配置
     */
    private Security security = new Security();
    
    /**
     * 搜索配置
     */
    private Search search = new Search();
    
    /**
     * 工作流配置
     */
    private Workflow workflow = new Workflow();
    
    /**
     * AI配置
     */
    private AI ai = new AI();
    
    @Data
    public static class Storage {
        /**
         * 存储类型（local/oss/s3）
         */
        private String type = "local";
        
        /**
         * 本地存储根路径
         */
        private String localBasePath = "/data/documents";
        
        /**
         * OSS端点
         */
        private String ossEndpoint;
        
        /**
         * OSS访问密钥ID
         */
        private String ossAccessKeyId;
        
        /**
         * OSS访问密钥密码
         */
        private String ossAccessKeySecret;
        
        /**
         * OSS存储桶名称
         */
        private String ossBucketName;
        
        /**
         * 是否启用自动备份
         */
        private boolean enableBackup = true;
        
        /**
         * 是否启用文件去重
         */
        private boolean enableDeduplication = true;
        
        /**
         * 单文件大小限制（MB）
         */
        private long maxFileSize = 100;
        
        /**
         * 允许的文件类型
         */
        private String[] allowedFileTypes = {"doc", "docx", "pdf", "txt"};
    }
    
    @Data
    public static class Cache {
        /**
         * 本地缓存大小
         */
        private int localCacheSize = 1000;
        
        /**
         * 本地缓存过期时间（秒）
         */
        private int localCacheExpire = 3600;
        
        /**
         * 分布式缓存前缀
         */
        private String distributedPrefix = "doc:";
        
        /**
         * 分布式缓存过期时间（秒）
         */
        private int distributedExpire = 7200;
    }
    
    @Data
    public static class Security {
        /**
         * 是否启用访问控制
         */
        private boolean enableAccessControl = true;
        
        /**
         * 是否启用水印
         */
        private boolean enableWatermark = true;
        
        /**
         * 是否启用加密存储
         */
        private boolean enableEncryption = true;
        
        /**
         * 加密算法
         */
        private String encryptionAlgorithm = "AES";
        
        /**
         * 水印内容模板
         */
        private String watermarkTemplate = "{username} {datetime}";
    }
    
    @Data
    public static class Search {
        /**
         * 索引类型（elasticsearch/solr）
         */
        private String type = "elasticsearch";
        
        /**
         * 是否启用全文检索
         */
        private boolean enableFulltext = true;
        
        /**
         * 是否启用相关推荐
         */
        private boolean enableRecommendation = true;
        
        /**
         * 索引刷新间隔（秒）
         */
        private int indexRefreshInterval = 30;
    }
    
    @Data
    public static class Workflow {
        /**
         * 是否启用审批流程
         */
        private boolean enableApproval = true;
        
        /**
         * 是否启用传阅流程
         */
        private boolean enableCirculation = true;
        
        /**
         * 是否启用归档流程
         */
        private boolean enableArchive = true;
    }
    
    @Data
    public static class AI {
        /**
         * 是否启用智能分类
         */
        private boolean enableSmartClassification = true;
        
        /**
         * 是否启用智能标签
         */
        private boolean enableSmartTag = true;
        
        /**
         * 是否启用智能摘要
         */
        private boolean enableSmartSummary = true;
        
        /**
         * 是否启用敏感信息识别
         */
        private boolean enableSensitiveDetection = true;
        
        /**
         * AI服务类型（local/openai/azure）
         */
        private String type = "local";
    }
}
