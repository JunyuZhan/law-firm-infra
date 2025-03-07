package com.lawfirm.auth.security.token;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * JWT令牌服务实现类
 */
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements TokenService {
    
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenStore tokenStore;
    private final UserDetailsService userDetailsService;
    
    @Override
    public String generateToken(Authentication authentication) {
        // 从自定义Authentication获取用户名
        String username = authentication.getPrincipal().toString();
        // 使用用户名创建token
        return createToken(username);
    }
    
    @Override
    public boolean validateToken(String token) {
        return token != null && jwtTokenProvider.validateToken(token);
    }
    
    @Override
    public Authentication getAuthenticationFromToken(String token) {
        // 创建自定义Authentication的实现
        return new Authentication() {
            @Override
            public Object getPrincipal() {
                return jwtTokenProvider.getUsername(token);
            }
            
            @Override
            public Object getCredentials() {
                return token;
            }
            
            @Override
            public boolean isAuthenticated() {
                return true;
            }
        };
    }
    
    @Override
    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    
    @Override
    public void removeToken(String token) {
        if (token != null) {
            String username = getUsernameFromToken(token);
            if (username != null) {
                tokenStore.removeToken(username);
            }
        }
    }
    
    @Override
    public String createToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        org.springframework.security.core.Authentication authentication = 
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        
        return jwtTokenProvider.createToken(authentication);
    }
    
    @Override
    public String refreshToken(String refreshToken) {
        if (refreshToken != null) {
            String username = tokenStore.getUsernameByRefreshToken(refreshToken);
            if (username != null && tokenStore.isRefreshTokenValid(username, refreshToken)) {
                return createToken(username);
            }
        }
        return null;
    }
    
    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsername(token);
    }
}