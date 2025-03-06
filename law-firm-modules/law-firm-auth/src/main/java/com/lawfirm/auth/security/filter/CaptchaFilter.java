package com.lawfirm.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.model.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final String LOGIN_URL = "/auth/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        if (LOGIN_URL.equals(request.getServletPath()) && request.getMethod().equals("POST")) {
            try {
                validateCaptcha(request);
            } catch (AuthenticationServiceException e) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write(
                    objectMapper.writeValueAsString(Result.error().message(e.getMessage()))
                );
                return;
            }
        }
        
        chain.doFilter(request, response);
    }

    private void validateCaptcha(HttpServletRequest request) {
        String captcha = request.getParameter("captcha");
        String captchaKey = request.getParameter("captchaKey");
        
        if (captcha == null || captchaKey == null) {
            throw new AuthenticationServiceException("验证码不能为空");
        }

        String key = CAPTCHA_PREFIX + captchaKey;
        String storedCaptcha = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key); // 验证码一次性使用

        if (storedCaptcha == null) {
            throw new AuthenticationServiceException("验证码已过期");
        }

        if (!captcha.equalsIgnoreCase(storedCaptcha)) {
            throw new AuthenticationServiceException("验证码错误");
        }
    }
} 