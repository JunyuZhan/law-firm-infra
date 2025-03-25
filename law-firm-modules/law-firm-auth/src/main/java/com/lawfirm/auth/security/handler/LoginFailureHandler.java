package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    
    private final LoginHistoryService loginHistoryService;
    private final ObjectMapper objectMapper;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String errorMessage = getErrorMessage(exception);
        
        // 记录登录失败历史
        try {
            loginHistoryService.saveLoginHistory(
                null, // 登录失败时可能无法获取用户ID
                username, 
                SecurityUtils.getClientIp(request), 
                "", // 地理位置，可通过IP获取
                request.getHeader("User-Agent"), // 浏览器信息
                "", // 操作系统信息，可从User-Agent解析
                1, // 失败状态
                errorMessage
            );
        } catch (Exception e) {
            log.error("记录登录历史失败", e);
        }
        
        // 返回登录失败结果
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
            CommonResult.error(HttpStatus.UNAUTHORIZED.value(), errorMessage)
        ));
        
        log.info("用户 {} 登录失败: {}", username, errorMessage);
    }
    
    /**
     * 根据异常类型获取错误消息
     */
    private String getErrorMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "用户名或密码错误";
        } else if (exception instanceof LockedException) {
            return "账户已锁定";
        } else if (exception instanceof DisabledException) {
            return "账户已禁用";
        } else if (exception instanceof AccountExpiredException) {
            return "账户已过期";
        } else if (exception instanceof CredentialsExpiredException) {
            return "密码已过期";
        } else {
            return "登录失败: " + exception.getMessage();
        }
    }
} 