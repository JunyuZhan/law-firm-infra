package com.lawfirm.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JSON登录过滤器
 * 用于处理JSON格式的登录请求
 */
@Slf4j
public class JsonLoginFilter extends AbstractAuthenticationProcessingFilter {
    
    private ObjectMapper objectMapper;
    
    /**
     * 私有构造函数，防止直接实例化
     * 
     * @param requestMatcher 请求匹配器
     */
    private JsonLoginFilter(AntPathRequestMatcher requestMatcher) {
        super(requestMatcher);
    }
    
    /**
     * 创建JSON登录过滤器的静态工厂方法
     * 
     * @param loginUrl 登录URL
     * @param authenticationManager 认证管理器
     * @param objectMapper 对象映射器
     * @return JSON登录过滤器实例
     */
    public static JsonLoginFilter create(String loginUrl, AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        JsonLoginFilter filter = new JsonLoginFilter(new AntPathRequestMatcher(loginUrl, "POST"));
        filter.setAuthenticationManager(authenticationManager);
        filter.objectMapper = objectMapper;
        return filter;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // 从请求体中读取JSON数据
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        
        try {
            // 将JSON转换为LoginDTO对象
            LoginDTO loginDTO = objectMapper.readValue(body, LoginDTO.class);
            
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            
            // 设置请求详情
            authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
            
            // 执行认证
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            log.error("JSON登录解析失败", e);
            throw new AuthenticationException("JSON登录解析失败: " + e.getMessage()) {};
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // 交给成功处理器处理
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                             AuthenticationException failed)
            throws IOException, ServletException {
        // 交给失败处理器处理
        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
} 