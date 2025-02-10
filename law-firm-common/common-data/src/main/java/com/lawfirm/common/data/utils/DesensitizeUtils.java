package com.lawfirm.common.data.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏工具类
 */
public class DesensitizeUtils {
    
    /**
     * 手机号脱敏
     */
    public static String maskPhone(String phone) {
        if (StringUtils.isBlank(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    
    /**
     * 邮箱脱敏
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email) || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        
        if (name.length() <= 2) {
            return "*".repeat(name.length()) + "@" + domain;
        }
        
        return name.charAt(0) + 
               "***" +
               name.charAt(name.length() - 1) + 
               "@" + domain;
    }
    
    /**
     * 身份证号脱敏
     */
    public static String maskIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }
        return idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1********$2");
    }
    
    /**
     * 银行卡号脱敏
     */
    public static String maskBankCard(String bankCard) {
        if (StringUtils.isBlank(bankCard)) {
            return bankCard;
        }
        return bankCard.replaceAll("(\\d{4})\\d{8}(\\d{4})", "$1********$2");
    }
    
    /**
     * 地址脱敏
     */
    public static String maskAddress(String address) {
        if (StringUtils.isBlank(address)) {
            return address;
        }
        return address.length() <= 8 ? address : address.substring(0, 8) + "****";
    }
    
    /**
     * 姓名脱敏
     */
    public static String maskName(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        return name.length() <= 1 ? name : name.substring(0, 1) + "**";
    }
} 