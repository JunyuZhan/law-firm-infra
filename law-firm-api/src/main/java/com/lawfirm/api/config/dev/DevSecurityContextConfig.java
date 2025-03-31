package com.lawfirm.api.config.dev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

/**
 * 开发环境安全上下文配置
 * <p>
 * 提供简化的认证设置，主要用于开发和测试阶段，绕过数据库认证
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevSecurityContextConfig {

    /**
     * 创建一个始终认证成功的AuthenticationManager
     * 主要用于开发测试环境
     */
    @Bean("authenticationManager")
    @Order(80)
    public AuthenticationManager devAuthenticationManager() {
        log.info("配置开发环境测试认证管理器");
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                log.debug("开发环境测试认证管理器: 自动通过认证 {}", authentication.getName());
                
                // 这里可以添加默认的用户信息和角色
                Collection<GrantedAuthority> authorities = Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
                );
                
                // 创建一个已认证的Authentication对象
                UserDetails userDetails = new User(
                    authentication.getName(), 
                    "password", 
                    true, true, true, true, 
                    authorities
                );
                
                // 设置到当前安全上下文
                UsernamePasswordAuthenticationToken result = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        authentication.getCredentials(),
                        authorities
                    );
                
                result.setDetails(authentication.getDetails());
                
                // 更新安全上下文
                SecurityContextHolder.getContext().setAuthentication(result);
                
                return result;
            }
        };
    }
    
    /**
     * 开发环境密码编码器
     */
    @Bean("devTestPasswordEncoder")
    public PasswordEncoder devPasswordEncoder() {
        log.info("配置开发环境测试密码编码器");
        return new PasswordEncoder() {
            private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();
            
            @Override
            public String encode(CharSequence rawPassword) {
                return delegate.encode(rawPassword);
            }
            
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // 开发模式下，所有密码都匹配成功
                log.debug("开发环境测试密码编码器: 自动匹配密码");
                return true;
            }
        };
    }
} 