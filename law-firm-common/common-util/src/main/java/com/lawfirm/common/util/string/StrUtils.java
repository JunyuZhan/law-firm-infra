package com.lawfirm.common.util.string;

import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.StrUtil;
import java.util.regex.Pattern;

@Slf4j
public class StrUtils {
    private static final Pattern CAMEL_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern UNDERSCORE_PATTERN = Pattern.compile("_([a-z])");
    
    public static String camelToUnderscore(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return CAMEL_PATTERN.matcher(str)
                .replaceAll(match -> "_" + match.group().toLowerCase())
                .replaceAll("^_", "");
    }
    
    public static String underscoreToCamel(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return UNDERSCORE_PATTERN.matcher(str)
                .replaceAll(match -> match.group(1).toUpperCase());
    }
    
    public static String maskPhone(String phone) {
        if (StrUtil.isBlank(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    
    public static String maskEmail(String email) {
        if (StrUtil.isBlank(email) || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        
        if (name.length() <= 2) {
            return "*".repeat(name.length()) + "@" + domain;
        }
        
        return name.charAt(0) + 
               "*".repeat(name.length() - 2) + 
               name.charAt(name.length() - 1) + 
               "@" + domain;
    }
    
    public static boolean isBlank(String str) {
        return StrUtil.isBlank(str);
    }
    
    public static boolean isNotBlank(String str) {
        return StrUtil.isNotBlank(str);
    }
} 