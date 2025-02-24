package com.lawfirm.common.security.crypto;

/**
 * 加密服务接口
 * 提供安全相关的加密、解密、哈希等操作
 */
public interface EncryptionService {
    
    /**
     * 加密
     * @param plaintext 明文
     * @return 密文
     */
    String encrypt(String plaintext);
    
    /**
     * 解密
     * @param ciphertext 密文
     * @return 明文
     */
    String decrypt(String ciphertext);
    
    /**
     * 生成密码哈希
     * @param password 原始密码
     * @return 哈希后的密码
     */
    String hashPassword(String password);
    
    /**
     * 验证密码
     * @param rawPassword 原始密码
     * @param encodedPassword 哈希后的密码
     * @return 是否匹配
     */
    boolean matchesPassword(String rawPassword, String encodedPassword);
    
    /**
     * 生成数据签名
     * @param data 待签名数据
     * @return 签名
     */
    String sign(String data);
    
    /**
     * 验证数据签名
     * @param data 原始数据
     * @param signature 签名
     * @return 是否有效
     */
    boolean verifySignature(String data, String signature);
} 