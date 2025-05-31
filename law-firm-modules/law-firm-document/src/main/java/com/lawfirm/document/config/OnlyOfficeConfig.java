package com.lawfirm.document.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OnlyOffice配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "lawfirm.onlyoffice")
public class OnlyOfficeConfig {

    /**
     * 是否启用OnlyOffice
     */
    private boolean enabled = false;

    /**
     * OnlyOffice服务器地址
     */
    private String url = "http://localhost:8088";

    /**
     * JWT密钥
     */
    private String secret = "your-jwt-secret-key";

    /**
     * JWT头部名称
     */
    private String header = "Authorization";

    /**
     * 回调URL
     */
    private String callbackUrl = "http://localhost:8080/api/v1/documents/callback";

    /**
     * 文档最大大小（字节）
     */
    private long maxFileSize = 50 * 1024 * 1024; // 50MB

    /**
     * 支持的文档格式
     */
    private String[] supportedFormats = {
        "doc", "docx", "odt", "rtf", "txt",
        "xls", "xlsx", "ods", "csv",
        "ppt", "pptx", "odp"
    };

    /**
     * 编辑超时时间（分钟）
     */
    private int editTimeout = 60;

    /**
     * 协同编辑配置
     */
    private CoEditConfig coEdit = new CoEditConfig();

    @Data
    public static class CoEditConfig {
        /**
         * 启用协同编辑
         */
        private boolean enabled = true;

        /**
         * 最大协同编辑用户数
         */
        private int maxUsers = 10;

        /**
         * 自动保存间隔（秒）
         */
        private int autoSaveInterval = 30;
    }
} 