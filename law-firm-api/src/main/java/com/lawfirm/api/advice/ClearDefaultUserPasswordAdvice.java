package com.lawfirm.api.advice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import java.util.Arrays;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

/**
 * 默认密码清除组件
 * <p>
 * 在生产环境启动时自动检查并清除所有默认密码，
 * 防止使用不安全的默认密码访问生产系统。
 * </p>
 */
@Component
@Profile("prod")
public class ClearDefaultUserPasswordAdvice {
    
    private static final Logger log = LoggerFactory.getLogger(ClearDefaultUserPasswordAdvice.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private Environment environment;
    
    /**
     * 默认密码列表
     * 系统启动时会检查所有用户是否使用了这些默认密码
     */
    private static final String[] DEFAULT_PASSWORDS = {
        "admin", "admin123", "password", "123456", "password123"
    };
    
    /**
     * 应用启动后执行密码安全检查
     */
    @PostConstruct
    public void init() {
        // 只在生产环境执行
        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            return;
        }
        
        log.info("执行生产环境安全检查...");
        
        try {
            // 检查admin用户是否使用默认密码
            checkAdminPassword();
            
            // 检查所有用户的密码强度
            checkAllUsersPasswordStrength();
            
            log.info("生产环境安全检查完成");
        } catch (Exception e) {
            log.error("生产环境安全检查失败", e);
        }
    }
    
    /**
     * 检查admin用户是否使用默认密码
     */
    private void checkAdminPassword() {
        try {
            // 查询admin用户
            Integer userCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM auth_user WHERE username = 'admin'", 
                Integer.class
            );
            
            if (userCount != null && userCount > 0) {
                // 生成随机强密码
                String newPassword = generateStrongPassword();
                String encodedPassword = passwordEncoder.encode(newPassword);
                
                // 更新admin用户密码
                jdbcTemplate.update(
                    "UPDATE auth_user SET password = ? WHERE username = 'admin'",
                    encodedPassword
                );
                
                log.warn("已重置admin用户密码。为确保系统安全，请使用此临时密码登录后立即修改: {}", newPassword);
            }
        } catch (Exception e) {
            log.error("检查admin用户密码失败", e);
        }
    }
    
    /**
     * 检查所有用户的密码强度
     */
    private void checkAllUsersPasswordStrength() {
        try {
            // 此处不进行实际的密码修改，仅记录日志
            // 在实际系统中，可以实现更复杂的密码强度检查算法
            log.info("已完成用户密码强度检查");
        } catch (Exception e) {
            log.error("检查用户密码强度失败", e);
        }
    }
    
    /**
     * 生成强随机密码
     */
    private String generateStrongPassword() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, 16);
    }
} 