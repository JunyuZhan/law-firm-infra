package com.lawfirm.common.util.crypto;

import com.lawfirm.common.util.BaseUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

/**
 * 加密工具类
 */
@Slf4j
public class CryptoUtils extends BaseUtils {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    /**
     * AES加密
     */
    public static String encryptAES(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM));
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            return null;
        }
    }
    
    /**
     * AES解密
     */
    public static String decryptAES(String encrypted, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM));
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES解密失败", e);
            return null;
        }
    }
    
    // DES加密
    public static String desEncrypt(String content, String key) {
        try {
            DES des = SecureUtil.des(key.getBytes(StandardCharsets.UTF_8));
            return des.encryptHex(content);
        } catch (Exception e) {
            log.error("DES加密失败", e);
            return null;
        }
    }
    
    public static String desDecrypt(String encrypted, String key) {
        try {
            DES des = SecureUtil.des(key.getBytes(StandardCharsets.UTF_8));
            return des.decryptStr(encrypted);
        } catch (Exception e) {
            log.error("DES解密失败", e);
            return null;
        }
    }
    
    // RSA加密
    private static final RSA RSA = new RSA();
    
    public static KeyPair generateKeyPair() {
        return SecureUtil.generateKeyPair("RSA");
    }
    
    public static String rsaEncrypt(String content, PublicKey publicKey) {
        try {
            RSA rsa = new RSA(null, publicKey);
            return rsa.encryptHex(content, KeyType.PublicKey);
        } catch (Exception e) {
            log.error("RSA加密失败", e);
            return null;
        }
    }
    
    public static String rsaDecrypt(String encrypted, PrivateKey privateKey) {
        try {
            RSA rsa = new RSA(privateKey, null);
            return rsa.decryptStr(encrypted, KeyType.PrivateKey);
        } catch (Exception e) {
            log.error("RSA解密失败", e);
            return null;
        }
    }
} 