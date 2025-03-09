package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.mapper.LoginHistoryMapper;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private final LoginHistoryMapper loginHistoryMapper;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        // 记录登录成功历史
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUsername(username);
        loginHistory.setIp(SecurityUtils.getClientIp(request));
        loginHistory.setLocation(""); // 可以通过IP获取地理位置
        loginHistory.setBrowser(request.getHeader("User-Agent"));
        loginHistory.setOs("");
        loginHistory.setStatus(0); // 成功
        loginHistory.setMsg("登录成功");
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistoryMapper.insert(loginHistory);
        
        // 获取密码参数
        String password = request.getParameter("password");
        
        // 使用AuthService的login方法
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
        LoginVO loginVO = authService.login(loginDTO);
        
        // 返回登录成功结果
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(loginVO)));
        
        log.info("用户 {} 登录成功", username);
    }
} 