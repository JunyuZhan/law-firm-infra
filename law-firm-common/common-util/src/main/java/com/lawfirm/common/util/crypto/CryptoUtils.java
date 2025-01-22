package com.lawfirm.common.util.crypto;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoUtils {
    
    // AES加密
    public static String aesEncrypt(String content, String key) {
        try {
            AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
            return aes.encryptHex(content);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            return null;
        }
    }
    
    public static String aesDecrypt(String encrypted, String key) {
        try {
            AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
            return aes.decryptStr(encrypted);
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