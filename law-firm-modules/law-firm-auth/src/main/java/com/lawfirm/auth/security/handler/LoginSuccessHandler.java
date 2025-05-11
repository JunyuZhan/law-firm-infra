package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.service.LoginHistoryService;
import com.lawfirm.model.auth.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 登录成功处理器
 * 重构后不再依赖AuthService，直接使用JwtTokenProvider生成token
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "law-firm.database.enabled", havingValue = "true", matchIfMissing = true)
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private final LoginHistoryService loginHistoryService;
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider tokenProvider;
    private final Environment environment;
    
    public LoginSuccessHandler(
            LoginHistoryService loginHistoryService, 
            @Qualifier("commonWebObjectMapper") ObjectMapper objectMapper, 
            JwtTokenProvider tokenProvider,
            Environment environment) {
        this.loginHistoryService = loginHistoryService;
        this.objectMapper = objectMapper;
        this.tokenProvider = tokenProvider;
        this.environment = environment;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        // 检查是否启用数据库功能
        boolean isDatabaseEnabled = Boolean.parseBoolean(
            environment.getProperty("law-firm.database.enabled", "true"));
            
        // 记录登录成功历史 - 只在数据库功能启用时记录
        if (isDatabaseEnabled && loginHistoryService != null) {
            try {
                Long userId = getUserIdFromUserDetails(userDetails);
                String ip = SecurityUtils.getClientIp(request);
                String userAgent = request.getHeader("User-Agent");
                loginHistoryService.saveLoginHistory(
                    userId, 
                    username, 
                    ip, 
                    "", // 地理位置，可通过IP获取
                    userAgent, // 浏览器信息
                    "", // 操作系统信息，可从User-Agent解析
                    0, // 成功状态
                    "登录成功"
                );
                log.info("记录登录历史成功，用户: {}, IP: {}", username, ip);
            } catch (Exception e) {
                log.error("记录登录历史失败", e);
            }
        } else {
            log.info("数据库功能已禁用或LoginHistoryService未注入，跳过登录历史记录");
        }
        
        // 直接使用tokenProvider生成token，而不是调用AuthService
        TokenDTO tokenDTO = tokenProvider.createToken(username, userDetails.getAuthorities());
        
        // 构建登录响应
        LoginVO loginVO = new LoginVO();
        if (userDetails instanceof SecurityUserDetails) {
            loginVO.setUserId(((SecurityUserDetails) userDetails).getUserId());
        } else {
            loginVO.setUserId(1L); // 默认ID
        }
        loginVO.setUsername(username);
        loginVO.setToken(tokenDTO);
        
        // 返回登录成功结果
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(loginVO)));
        
        log.info("用户 {} 登录成功", username);
    }
    
    /**
     * 从UserDetails中获取用户ID
     * 注意：这里需要根据实际的UserDetails实现类来获取用户ID
     */
    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // 如果是SecurityUserDetails，直接获取userId
        if (userDetails instanceof SecurityUserDetails) {
            return ((SecurityUserDetails) userDetails).getUserId();
        }
        // 默认返回1L，实际项目中应该根据用户名查询用户ID
        return 1L;
    }
} 