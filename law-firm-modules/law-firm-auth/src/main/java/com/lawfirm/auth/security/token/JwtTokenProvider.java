package com.lawfirm.auth.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final SecretKey key;
    private final TokenStore tokenStore;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(TokenStore tokenStore, UserDetailsService userDetailsService) {
        // 使用推荐的方式创建密钥
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("bGF3ZmlybVNlY3JldEtleUZvckp3dEF1dGhlbnRpY2F0aW9uQW5kQXV0aG9yaXphdGlvbkluMjAyMw=="));
        this.tokenStore = tokenStore;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration * 1000);

        // 使用新的 API 创建 JWT
        String token = Jwts.builder()
                .claim("username", userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
                
        // 保存 token 到存储
        tokenStore.storeToken(userDetails.getUsername(), token);
        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.get("username", String.class);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.get("username", String.class);
            return !claims.getExpiration().before(new Date()) && tokenStore.isTokenValid(username, token);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }
}