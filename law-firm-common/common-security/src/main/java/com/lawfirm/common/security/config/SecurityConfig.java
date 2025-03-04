package com.lawfirm.common.security.config;

import com.lawfirm.common.security.crypto.CryptoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全配置类
 */
@Configuration
public class SecurityConfig {

    @Bean
    public CryptoService cryptoService() {
        return new CryptoService();
    }
} 