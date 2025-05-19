package com.lawfirm.api.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 配置加密功能
 * 用于处理敏感信息如数据库密码的加密
 */
@Slf4j
@Configuration("encryptionConfig")
@EnableEncryptableProperties
public class EncryptionConfig {

    @Autowired
    private Environment environment;

    /**
     * 创建字符串加密器
     * 使用系统环境变量JASYPT_PASSWORD作为加密密钥
     * 如果未设置，则使用默认密钥（仅用于开发）
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        // 从环境变量获取密钥，开发环境可使用默认值，生产环境必须配置
        String jasyptPassword = System.getenv("JASYPT_PASSWORD");
        String activeProfile = environment.getActiveProfiles().length > 0 ? 
                environment.getActiveProfiles()[0] : "dev";
                
        if (jasyptPassword == null) {
            if ("prod".equals(activeProfile)) {
                throw new IllegalStateException("生产环境必须设置加密密钥环境变量JASYPT_PASSWORD");
            } else {
                // 仅开发环境使用默认密钥
                jasyptPassword = "law-firm-dev-default-key";
                log.warn("使用开发环境默认密钥，生产环境必须配置JASYPT_PASSWORD环境变量");
            }
        }
        
        config.setPassword(jasyptPassword);
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