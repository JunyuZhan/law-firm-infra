package com.lawfirm.common.util.string;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类，扩展自 Apache Commons Lang 的 StringUtils
 */
public class StringExtUtils extends StringUtils {
    
    /**
     * 截取字符串，超出部分用省略号代替
     *
     * @param str 原字符串
     * @param maxLength 最大长度
     * @return 处理后的字符串
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
    
    /**
     * 移除字符串中的所有空白字符
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String removeAllWhitespace(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s+", "");
    }
    
    /**
     * 将字符串的首字母转为大写
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
    /**
     * 将字符串的首字母转为小写
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String uncapitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
    
    /**
     * 将驼峰式命名转换为下划线命名
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String camelToUnderscore(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean lastUpperCase = false;
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean upperCase = Character.isUpperCase(c);
            if (upperCase) {
                if (i == 0) {
                    sb.append('_');
                    sb.append(Character.toLowerCase(c));
                } else if (!lastUpperCase) {
                    sb.append('_');
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                sb.append(c);
            }
            lastUpperCase = upperCase;
        }
        return sb.toString();
    }
    
    /**
     * 将下划线命名转换为驼峰式命名
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String underscoreToCamel(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {  // 只有在不是第一个字符时，才设置nextUpper为true
                    nextUpper = true;
                }
            } else {
                if (nextUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * 检查字符串是否包含中文字符
     *
     * @param str 要检查的字符串
     * @return 是否包含中文字符
     */
    public static boolean containsChinese(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches(".*[\\u4e00-\\u9fa5].*");
    }
    
    /**
     * 获取字符串的字节长度（UTF-8编码）
     *
     * @param str 要检查的字符串
     * @return 字节长度
     */
    public static int getByteLength(String str) {
        if (str == null) {
            return 0;
        }
        return str.getBytes().length;
    }
    
    /**
     * 手机号码脱敏
     * 将中间4位数字替换为****
     *
     * @param phone 手机号码
     * @return 脱敏后的手机号码
     */
    public static String maskPhone(String phone) {
        if (isBlank(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    
    /**
     * 邮箱地址脱敏
     * 将@前面的部分进行脱敏处理
     *
     * @param email 邮箱地址
     * @return 脱敏后的邮箱地址
     */
    public static String maskEmail(String email) {
        if (isBlank(email) || !email.contains("@")) {
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
} 