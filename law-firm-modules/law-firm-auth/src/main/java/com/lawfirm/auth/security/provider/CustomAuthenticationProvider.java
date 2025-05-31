package com.lawfirm.auth.security.provider;

import com.lawfirm.auth.security.details.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证提供者
 */
@Slf4j
@Component("customAuthenticationProvider")
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // 加载用户详情
        SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(username);
        
        // 验证密码
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.warn("用户 {} 密码错误", username);
            throw new BadCredentialsException("密码错误");
        }
        
        // 检查用户状态
        if (!userDetails.isEnabled()) {
            log.warn("用户 {} 已禁用", username);
            throw new BadCredentialsException("用户已禁用");
        }
        
        if (!userDetails.isAccountNonLocked()) {
            log.warn("用户 {} 已锁定", username);
            throw new BadCredentialsException("用户已锁定");
        }
        
        if (!userDetails.isAccountNonExpired()) {
            log.warn("用户 {} 已过期", username);
            throw new BadCredentialsException("用户已过期");
        }
        
        if (!userDetails.isCredentialsNonExpired()) {
            log.warn("用户 {} 凭证已过期", username);
            throw new BadCredentialsException("凭证已过期");
        }
        
        // 创建已认证的令牌
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        
        log.info("用户 {} 认证成功", username);
        return authToken;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}