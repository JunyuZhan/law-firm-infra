package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 简化版登录处理器
 * 不依赖数据库，适用于开发环境
 * 同时处理登录成功和失败
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "false")
public class SimpleLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider tokenProvider;
    
    public SimpleLoginHandler(
            @Qualifier("objectMapper") ObjectMapper objectMapper,
            JwtTokenProvider tokenProvider) {
        this.objectMapper = objectMapper;
        this.tokenProvider = tokenProvider;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        log.info("开发环境模式：用户 {} 登录成功", username);
        
        // 生成token
        TokenDTO tokenDTO = tokenProvider.createToken(username, userDetails.getAuthorities());
        
        // 构建登录响应
        LoginVO loginVO = new LoginVO();
        if (userDetails instanceof SecurityUserDetails) {
            loginVO.setUserId(((SecurityUserDetails) userDetails).getUserId());
        } else {
            loginVO.setUserId(1L); // 开发环境默认ID
        }
        loginVO.setUsername(username);
        loginVO.setToken(tokenDTO);
        
        // 返回登录成功结果
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(loginVO)));
    }
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String errorMessage = exception instanceof BadCredentialsException ? 
                "用户名或密码错误" : "登录失败: " + exception.getMessage();
        
        log.info("开发环境模式：用户 {} 登录失败: {}", username, errorMessage);
        
        // 返回登录失败结果
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
            CommonResult.error(HttpStatus.UNAUTHORIZED.value(), errorMessage)
        ));
    }
} 