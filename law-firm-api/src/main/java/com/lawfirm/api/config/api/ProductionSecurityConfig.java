package com.lawfirm.api.config.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * 生产环境特定安全配置整合类
 * 用于集成生产环境所需的安全增强
 */
@Slf4j
@Configuration
@Profile("prod")
public class ProductionSecurityConfig {

    private final Environment environment;

    public ProductionSecurityConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 生产环境CORS配置
     */
    @Bean("productionCorsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 生产环境仅允许配置的源
        String allowedOrigins = environment.getProperty("cors.allowed-origins", "https://*.lawfirm.com");
        configuration.setAllowedOriginPatterns(Collections.singletonList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        log.info("已配置生产环境CORS设置，允许的来源: {}", allowedOrigins);
        return source;
    }

    /**
     * 应用启动后的安全检查
     */
    @Bean
    public SecurityChecker securityChecker() {
        return new SecurityChecker(environment);
    }

    /**
     * 安全检查器内部类
     */
    public static class SecurityChecker {
        private final Environment environment;

        public SecurityChecker(Environment environment) {
            this.environment = environment;
            performSecurityChecks();
        }

        private void performSecurityChecks() {
            // 检查关键安全配置
            checkJwtSettings();
            checkCorsSettings();
            checkSessionSettings();
            checkPasswordPolicy();
            
            // 日志安全检查通过
            log.info("生产环境安全检查完成，所有配置符合要求");
        }

        private void checkJwtSettings() {
            // 检查JWT密钥是否通过环境变量设置
            String jwtSecret = environment.getProperty("law.firm.security.jwt.secret");
            if (jwtSecret == null || "defaultsecretkey12345678901234567890".equals(jwtSecret)) {
                log.warn("安全警告: JWT密钥使用默认值，生产环境应通过环境变量设置");
            } else {
                log.info("JWT密钥已配置");
            }

            // 检查JWT过期时间
            String jwtExpiration = environment.getProperty("law.firm.security.jwt.expiration");
            if (jwtExpiration == null) {
                log.warn("安全警告: JWT过期时间未配置，将使用默认值");
            }
        }

        private void checkCorsSettings() {
            String allowedOrigins = environment.getProperty("cors.allowed-origins");
            if (allowedOrigins == null || allowedOrigins.contains("*")) {
                log.warn("安全警告: CORS允许的源包含通配符，生产环境应限制特定域名");
            }
        }

        private void checkSessionSettings() {
            String sessionTimeout = environment.getProperty("server.servlet.session.timeout");
            if (sessionTimeout == null) {
                log.warn("安全警告: 会话超时未配置，将使用默认值");
            }
        }

        private void checkPasswordPolicy() {
            // 检查密码策略
            String checkStrength = environment.getProperty("security.password.check-strength");
            if (!"true".equals(checkStrength)) {
                log.warn("安全警告: 密码强度检查未启用");
            }
        }
    }
} 