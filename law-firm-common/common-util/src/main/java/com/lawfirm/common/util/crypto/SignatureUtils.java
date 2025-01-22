package com.lawfirm.common.util.crypto;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

@Slf4j
public class SignatureUtils {
    
    private static final String SIGN_ALGORITHM = "SHA256withRSA";
    
    public static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            log.error("签名失败", e);
            return null;
        }
    }
    
    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
        try {
            Signature sig = Signature.getInstance(SIGN_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(data);
            return sig.verify(signature);
        } catch (Exception e) {
            log.error("验签失败", e);
            return false;
        }
    }
    
    public static String signHex(String content, PrivateKey privateKey) {
        try {
            byte[] signed = sign(content.getBytes(StandardCharsets.UTF_8), privateKey);
            return Base64.encode(signed);
        } catch (Exception e) {
            log.error("签名失败", e);
            return null;
        }
    }
    
    public static boolean verifyHex(String content, String signature, PublicKey publicKey) {
        try {
            byte[] signedBytes = Base64.decode(signature);
            return verify(content.getBytes(StandardCharsets.UTF_8), signedBytes, publicKey);
        } catch (Exception e) {
            log.error("验签失败", e);
            return false;
        }
    }
} 