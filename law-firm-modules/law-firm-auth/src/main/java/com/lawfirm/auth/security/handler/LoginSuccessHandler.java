package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        // 创建认证对象
        com.lawfirm.common.security.authentication.Authentication authForToken = 
            new com.lawfirm.common.security.authentication.Authentication() {
                @Override
                public Object getPrincipal() {
                    return authentication.getName();
                }

                @Override
                public Object getCredentials() {
                    return authentication.getCredentials();
                }

                @Override
                public boolean isAuthenticated() {
                    return authentication.isAuthenticated();
                }
            };
            
        // 生成token
        String token = tokenService.generateToken(authForToken);
        
        // 构建响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(data)));
    }
} 