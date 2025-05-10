package com.lawfirm.common.security.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加密服务，提供加密和解密功能
 * 
 * 职责：
 * 1. 提供通用的加解密功能
 * 2. 不含特定业务逻辑，仅作为基础服务
 */
@Service("commonCryptoService")
public class CryptoService {
    private static final String ALGORITHM = "AES";
    
    @Value("${law.firm.security.crypto.secret-key:DefaultSecretKey16}")
    private String secretKeyValue; // 配置密钥，提供默认值但建议在应用中覆盖

    /**
     * 加密字符串
     */
    public String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(getSecretKeyBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密失败", e);
        }
    }

    /**
     * 解密字符串
     */
    public String decrypt(String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(getSecretKeyBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("解密失败", e);
        }
    }
    
    /**
     * 获取密钥字节数组，确保长度为16字节
     */
    private byte[] getSecretKeyBytes() {
        // 确保密钥长度为16字节(128位)
        byte[] keyBytes = secretKeyValue.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[16];
        // 如果密钥长度不足16字节，使用填充；如果超过，则截断
        System.arraycopy(keyBytes, 0, result, 0, Math.min(keyBytes.length, 16));
        return result;
    }
} 