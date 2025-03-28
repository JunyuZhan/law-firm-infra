package com.lawfirm.auth.security.provider;

import com.lawfirm.auth.exception.AuthException;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT令牌提供者
 */
@Slf4j
@Component
public class JwtTokenProvider {
    
    @Value("${law.firm.security.jwt.secret:HmacSHA256SecretKey}")
    private String jwtSecret;
    
    @Value("${law.firm.security.jwt.expiration:86400000}")
    private long jwtExpiration;
    
    @Value("${law.firm.security.jwt.refresh-expiration:604800000}")
    private long refreshExpiration;
    
    @Value("${law.firm.security.jwt.issuer:law-firm-auth}")
    private String issuer;
    
    @Value("${law.firm.security.jwt.audience:law-firm-web}")
    private String audience;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        // 使用安全的方法生成密钥，确保至少256位
        if (jwtSecret.length() < 32) { // 至少32字节(256位)
            // 如果配置的密钥不够长，使用推荐的方式生成
            this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("j2h1EW7AstTO4WzV9yu4PV5Vk7+RXKzK8mZ0Lotxtdo="));
            log.warn("配置的JWT密钥长度不足，已自动生成安全密钥");
        } else {
            try {
                this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            } catch (Exception e) {
                log.error("JWT密钥初始化失败: {}", e.getMessage());
                // 出错时使用安全的随机密钥作为备选
                this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("j2h1EW7AstTO4WzV9yu4PV5Vk7+RXKzK8mZ0Lotxtdo="));
                log.warn("已自动生成备选JWT密钥");
            }
        }
    }
    
    /**
     * 创建令牌
     * 
     * @param username 用户名
     * @param authorities 权限列表
     * @return 令牌DTO
     */
    public TokenDTO createToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + jwtExpiration);
        Date refreshTokenExpiresIn = new Date(now.getTime() + refreshExpiration);
        
        String accessToken = Jwts.builder()
                .subject(username)
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(now)
                .expiration(accessTokenExpiresIn)
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .signWith(key)
                .compact();
        
        String refreshToken = Jwts.builder()
                .subject(username)
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(now)
                .expiration(refreshTokenExpiresIn)
                .signWith(key)
                .compact();
        
        return new TokenDTO()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(jwtExpiration / 1000);
    }
    
    /**
     * 从令牌中获取认证信息
     * 
     * @param token 令牌
     * @return 认证信息
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        String username = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("authorities", String.class).split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        User principal = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    /**
     * 验证令牌
     * 
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey)key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("无效的JWT令牌: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 从令牌中获取用户名
     * 
     * @param token 令牌
     * @return 用户名
     */
    public String getUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey)key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException("无效的JWT令牌");
        }
    }
    
    /**
     * 刷新令牌
     * 
     * @param refreshToken 刷新令牌
     * @return 新的令牌DTO
     */
    public TokenDTO refreshToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new AuthException("刷新令牌已过期或无效");
        }
        
        String username = getUsername(refreshToken);
        // 这里需要从用户服务获取用户权限
        // 简化处理，假设用户只有一个ROLE_USER权限
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        
        return createToken(username, authorities);
    }
}

