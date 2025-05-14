package com.lawfirm.api.config;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.lawfirm.api.constants.ApiDocPaths;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API文档安全配置
 * 针对不同环境提供不同的安全控制策略
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.api-doc.secure-access.enabled", havingValue = "true", matchIfMissing = false)
// 临时禁用此配置，排除故障
public class ApiDocSecurityConfig {
    
    private final Environment environment;
    
    @Value("${law-firm.api-doc.secure-access.key:${APIDOC_ACCESS_KEY:apiAccessSecret}}")
    private String accessKey;
    
    /**
     * API文档安全过滤器链
     * 在生产环境中需要API密钥验证，非生产环境允许自由访问
     */
    @Bean
    @Order(1) // 确保比主安全配置优先级高
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API文档安全访问策略");
        
        // 创建请求匹配器
        RequestMatcher docPathsMatcher = new OrRequestMatcher(
            Arrays.stream(ApiDocPaths.ALL_DOC_PATHS)
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new)
        );
        
        return http
            .securityMatcher(docPathsMatcher)
            .authorizeHttpRequests(authorize -> {
                if (isProductionEnvironment()) {
                    log.info("生产环境API文档访问将启用密钥验证");
                    // 生产环境需要API密钥验证
                    authorize.anyRequest().access(this::checkApiKey);
                } else {
                    log.info("非生产环境API文档访问允许自由访问");
                    // 非生产环境允许自由访问
                    authorize.anyRequest().permitAll();
                }
            })
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .build();
    }
    
    /**
     * 检查API密钥是否有效
     */
    private AuthorizationDecision checkApiKey(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String providedKey = context.getRequest().getHeader("X-API-Doc-Key");
        boolean hasAccess = accessKey.equals(providedKey);
        
        if (!hasAccess) {
            log.warn("API文档访问被拒绝: {} - 缺少有效的API密钥", context.getRequest().getRequestURI());
        }
        
        return new AuthorizationDecision(hasAccess);
    }
    
    /**
     * 判断是否是生产环境
     */
    private boolean isProductionEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles).anyMatch(profile -> 
            profile.equalsIgnoreCase("prod") || profile.equalsIgnoreCase("production"));
    }
} 