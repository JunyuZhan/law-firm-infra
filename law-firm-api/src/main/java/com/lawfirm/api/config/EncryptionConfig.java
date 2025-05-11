package com.lawfirm.api.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置加密功能
 * 用于处理敏感信息如数据库密码的加密
 */
@Configuration("encryptionConfig")
@EnableEncryptableProperties
public class EncryptionConfig {

    /**
     * 创建字符串加密器
     * 使用系统环境变量JASYPT_PASSWORD作为加密密钥
     * 如果未设置，则使用默认密钥（仅用于开发）
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        // 从环境变量获取密钥，如未设置则使用默认值
        config.setPassword(System.getenv("JASYPT_PASSWORD") != null ? 
                System.getenv("JASYPT_PASSWORD") : "law-firm-default-key");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        
        encryptor.setConfig(config);
        return encryptor;
    }
} 