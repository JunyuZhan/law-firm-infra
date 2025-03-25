package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.security.handler.LoginSuccessHandler;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.LoginHistoryService;
import com.lawfirm.model.auth.vo.LoginVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 开发模式无Redis环境的模拟Bean配置
 * 用于打破循环依赖链
 */
@Configuration
@Profile("dev-noredis")
@ConditionalOnProperty(name = "dev.use-redis", havingValue = "false")
public class DevNoRedisMockBeanConfig {

    /**
     * 提供一个模拟的LoginHistoryService对象
     */
    @Bean
    @Primary
    public LoginHistoryService loginHistoryService() {
        // 返回一个模拟的LoginHistoryService
        return new LoginHistoryService() {
            @Override
            public boolean saveLoginHistory(Long userId, String username, String ip, String location, 
                                         String browser, String os, Integer status, String msg) {
                // 开发环境下只记录日志，不实际保存
                System.out.println("【DEV模式】记录登录历史: " + username);
                return true;
            }

            @Override
            public List<LoginHistory> getLoginHistory(Long userId) {
                // 返回空列表
                return new ArrayList<>();
            }

            @Override
            public LoginHistory getLastLogin(Long userId) {
                // 返回空的登录历史
                LoginHistory history = new LoginHistory();
                history.setUserId(userId);
                history.setUsername("admin");
                history.setIp("127.0.0.1");
                history.setLocation("本地开发环境");
                history.setBrowser("开发模式");
                history.setOs("开发模式");
                history.setStatus(0);
                history.setMsg("开发环境模拟登录");
                history.setLoginTime(LocalDateTime.now());
                return history;
            }
        };
    }
    
    /**
     * 提供一个模拟的ObjectMapper对象
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // 返回一个真实的ObjectMapper实例
        return new ObjectMapper();
    }
    
    /**
     * 提供一个模拟的AuthService对象
     */
    @Bean
    @Primary
    public AuthService authService() {
        // 返回一个模拟的AuthService
        return new AuthService() {
            @Override
            public LoginVO login(LoginDTO loginDTO) {
                // 开发环境下模拟登录成功
                LoginVO loginVO = new LoginVO();
                // 设置基本信息
                return loginVO;
            }

            @Override
            public void logout(String username) {
                // 开发环境下不执行任何操作
                System.out.println("【DEV模式】用户登出: " + username);
            }

            @Override
            public TokenDTO refreshToken(String refreshToken) {
                // 开发环境下返回模拟的token
                TokenDTO tokenDTO = new TokenDTO();
                tokenDTO.setAccessToken("dev-mode-access-token");
                tokenDTO.setRefreshToken("dev-mode-refresh-token");
                tokenDTO.setExpiresIn(3600L);
                tokenDTO.setTokenType("Bearer");
                return tokenDTO;
            }

            @Override
            public boolean validateCaptcha(String captcha, String captchaKey) {
                // 开发环境下总是返回true，验证码校验通过
                return true;
            }
        };
    }
    
    /**
     * 提供一个模拟的登录成功处理器，避免循环依赖
     */
    @Bean
    @Primary
    public LoginSuccessHandler loginSuccessHandler(
            LoginHistoryService loginHistoryService,
            ObjectMapper objectMapper,
            JwtTokenProvider tokenProvider) {
        // 使用上面创建的模拟服务创建LoginSuccessHandler
        return new LoginSuccessHandler(loginHistoryService, objectMapper, tokenProvider) {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                // 空实现，仅用于开发环境
                System.out.println("【DEV模式】用户登录成功: " + authentication.getName());
            }
        };
    }

    /**
     * 提供一个模拟的UserDetailsService实现
     */
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // 开发环境下，任何用户名都返回同样的用户
                System.out.println("【DEV模式】加载用户: " + username);
                
                // 如果用户名为admin，则返回admin用户
                if ("admin".equals(username)) {
                    return User.builder()
                            .username("admin")
                            .password(passwordEncoder().encode("admin"))
                            .authorities("ROLE_ADMIN")
                            .build();
                }
                
                // 其他用户名返回普通用户
                return User.builder()
                        .username(username)
                        .password(passwordEncoder().encode("password"))
                        .authorities("ROLE_USER")
                        .build();
            }
        };
    }
    
    /**
     * 提供一个模拟的密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供一个模拟的JwtTokenProvider
     */
    @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        // 使用无参构造函数
        return new JwtTokenProvider();
    }
} 