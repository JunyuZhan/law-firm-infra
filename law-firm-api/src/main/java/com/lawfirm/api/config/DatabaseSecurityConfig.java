package com.lawfirm.api.config;

import com.lawfirm.common.data.config.DataSourceEnhancementConfig.DatabasePasswordDecryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库安全配置
 * 
 * API模块特定的数据库安全配置，优先使用common-data模块提供的DatabasePasswordDecryptor
 * 避免重复实现加解密逻辑
 * 
 * 使用方式：
 * 1. 在配置文件中设置 law-firm.security.db-password.enabled=true
 * 2. 使用工具加密密码（格式：ENC(base64密文)）
 * 3. 在配置文件中设置 spring.datasource.password=ENC(加密后的密码)
 */
@Slf4j
@Configuration("apiDatabaseSecurityConfig")
@ConditionalOnProperty(name = "law-firm.security.db-password.enabled", havingValue = "true", matchIfMissing = false)
public class DatabaseSecurityConfig {

    @Autowired
    private Environment environment;

    /**
     * 提供API模块的数据库密码处理器
     * 
     * 仅在common-data模块的DatabasePasswordDecryptor不存在时创建
     */
    @Bean("apiDatabasePasswordProcessor")
    @ConditionalOnMissingBean(DatabasePasswordDecryptor.class)
    public ApiDatabasePasswordProcessor apiDatabasePasswordProcessor() {
        // 从环境变量或配置文件获取密钥
        String secretKey = environment.getProperty("LAW_FIRM_SECRET_KEY", 
                environment.getProperty("law-firm.security.db-password.secret-key", ""));
        
        log.info("创建API模块数据库密码处理器 (注意：推荐使用common-data模块的DatabasePasswordDecryptor)");
        return new ApiDatabasePasswordProcessor(secretKey);
    }

    /**
     * API模块数据库密码处理器
     * 
     * 是对common-data模块DatabasePasswordDecryptor的适配
     * 保留原接口但内部使用统一的加解密逻辑
     */
    public static class ApiDatabasePasswordProcessor {
        private final String secretKey;
        private final DatabasePasswordDecryptor decryptor;

        public ApiDatabasePasswordProcessor(String secretKey) {
            this.secretKey = secretKey;
            this.decryptor = new DatabasePasswordDecryptor(secretKey);
        }

        /**
         * 处理密码
         * 
         * @param password 原始密码
         * @return 处理后的密码
         */
        public String processPassword(String password) {
            if (password == null) {
                return password;
            }
            
            // 兼容旧格式 {encrypted}
            if (password.startsWith("{encrypted}")) {
                password = "ENC(" + password.substring("{encrypted}".length()) + ")";
            }
            
            // 使用通用解密器解密
            return decryptor.decrypt(password);
        }
    }
} 