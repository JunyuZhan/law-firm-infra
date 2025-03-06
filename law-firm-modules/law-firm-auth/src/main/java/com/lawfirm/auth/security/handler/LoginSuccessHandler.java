package com.lawfirm.auth.security.handler;

import com.lawfirm.auth.security.service.JwtTokenService;
import com.lawfirm.common.utils.JsonUtils;
import com.lawfirm.common.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;

    public LoginSuccessHandler(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        String token = jwtTokenService.generateToken(authentication);
        
        response.setContentType("application/json;charset=UTF-8");
        response.getOutputStream().write(
            JsonUtils.toJson(Result.ok().data("token", token))
                .getBytes(StandardCharsets.UTF_8)
        );
    }
} 