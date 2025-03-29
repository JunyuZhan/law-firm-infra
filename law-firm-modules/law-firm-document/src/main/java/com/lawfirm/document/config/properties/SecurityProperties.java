package com.lawfirm.document.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文档安全配置属性
 */
@Data
@Component("documentSecurityProperties")
@ConfigurationProperties(prefix = "law.firm.document.security")
public class SecurityProperties {
    
    /**
     * 加密算法
     */
    private String encryptionAlgorithm = "AES";
    
    /**
     * 密钥长度
     */
    private int keyLength = 256;
    
    /**
     * 是否启用文件加密
     */
    private boolean enableEncryption = true;
    
    /**
     * 是否启用数字签名
     */
    private boolean enableDigitalSignature = true;
    
    /**
     * 签名算法
     */
    private String signatureAlgorithm = "SHA256withRSA";
    
    /**
     * 是否启用访问控制
     */
    private boolean enableAccessControl = true;
    
    /**
     * 是否启用审计日志
     */
    private boolean enableAuditLog = true;
    
    /**
     * 审计日志保留天数
     */
    private int auditLogRetentionDays = 90;
    
    /**
     * 是否启用水印
     */
    private boolean enableWatermark = true;
    
    /**
     * 水印内容
     */
    private String watermarkContent = "CONFIDENTIAL";
    
    /**
     * 水印透明度
     */
    private float watermarkOpacity = 0.3f;
    
    /**
     * 是否启用防篡改
     */
    private boolean enableTamperProof = true;
    
    /**
     * 是否启用敏感信息过滤
     */
    private boolean enableSensitiveFilter = true;
    
    /**
     * 敏感信息过滤规则
     */
    private String[] sensitivePatterns = {
        "\\d{18}", // 身份证号
        "\\d{11}", // 手机号
        "\\d{16,19}", // 银行卡号
        "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}" // 邮箱
    };
}
