package com.lawfirm.auth.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码工具类
 */
public class PasswordUtils {
    
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    
    /**
     * 加密密码
     * 
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encode(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }
    
    /**
     * 验证密码
     * 
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }
    
    /**
     * 生成随机密码
     * 
     * @param length 密码长度
     * @return 随机密码
     */
    public static String generateRandomPassword(int length) {
        if (length < 8) {
            length = 8; // 最小长度为8
        }
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
    
    /**
     * 检查密码强度
     * 
     * @param password 密码
     * @return 密码强度（0-4，数字越大越强）
     */
    public static int checkPasswordStrength(String password) {
        int strength = 0;
        
        if (password.length() >= 8) {
            strength++;
        }
        
        if (password.matches(".*\\d.*")) {
            strength++;
        }
        
        if (password.matches(".*[a-z].*")) {
            strength++;
        }
        
        if (password.matches(".*[A-Z].*")) {
            strength++;
        }
        
        if (password.matches(".*[!@#$%^&*()_+].*")) {
            strength++;
        }
        
        return strength;
    }
}

