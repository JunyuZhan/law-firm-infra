package com.lawfirm.common.util.codec;

import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;

import cn.hutool.core.codec.Base64;

public class CodecUtils {
    
    public static String encodeBase64(String data) {
        return Base64.encode(data);
    }
    
    public static String decodeBase64(String base64) {
        return Base64.decodeStr(base64);
    }
    
    public static String encodeBase64(byte[] data) {
        return Base64.encode(data);
    }
    
    public static byte[] decodeBase64ToBytes(String base64) {
        return Base64.decode(base64);
    }
    
    public static String utf8ToIso88591(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
    
    public static String iso88591ToUtf8(String str) {
        return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
} 