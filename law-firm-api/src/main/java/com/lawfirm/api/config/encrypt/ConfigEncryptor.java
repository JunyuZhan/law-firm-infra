package com.lawfirm.api.config.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigEncryptor {

    @Value("${jasypt.encryptor.password}")
    private String encryptorPassword;

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;

    @Value("${jasypt.encryptor.provider-name}")
    private String providerName;

    private PooledPBEStringEncryptor encryptor;

    public String encrypt(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        try {
            return getEncryptor().encrypt(value);
        } catch (Exception e) {
            log.error("配置加密失败", e);
            return value;
        }
    }

    public String decrypt(String value) {
        if (value == null || value.isEmpty() || !value.startsWith("ENC(") || !value.endsWith(")")) {
            return value;
        }
        try {
            return getEncryptor().decrypt(value.substring(4, value.length() - 1));
        } catch (Exception e) {
            log.error("配置解密失败", e);
            return value;
        }
    }

    private PooledPBEStringEncryptor getEncryptor() {
        if (encryptor == null) {
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(encryptorPassword);
            config.setAlgorithm(algorithm);
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName(providerName);
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");

            encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(config);
        }
        return encryptor;
    }
} 