package com.lawfirm.auth.filter;

import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.service.AuthService;
import com.lawfirm.common.core.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // 获取token
            String token = getTokenFromRequest(request);
            
            if (StringUtils.hasText(token)) {
                // 验证token
                LoginUser loginUser = authService.verifyToken(token);
                
                // 将用户信息存入SecurityContext
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        loginUser,
                        null,
                        loginUser.getAuthorities()
                    );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (BusinessException e) {
            log.warn("Token验证失败: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Token验证异常", e);
        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头中获取token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token;
        }
        return null;
    }
} 