package com.lawfirm.common.security.config;

import com.lawfirm.common.security.crypto.CryptoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 通用安全配置类
 * 
 * 提供基础安全服务，如加密服务
 * 注意：此配置为基础安全服务，由专用安全模块（如auth）扩展
 */
@Configuration("commonSecurityConfig")
@Order(110)  // 优先级最低，作为基础配置
public class SecurityConfig {

    /**
     * 加密服务
     * 提供通用的加解密功能
     */
    @Bean("cryptoService")
    public CryptoService cryptoService() {
        return new CryptoService();
    }
} 