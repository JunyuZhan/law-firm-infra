package com.lawfirm.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.auth.entity.LoginHistory;
import com.lawfirm.model.auth.mapper.LoginHistoryMapper;
import com.lawfirm.common.core.api.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 登录失败处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    
    private final LoginHistoryMapper loginHistoryMapper;
    private final ObjectMapper objectMapper;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String errorMessage = "认证失败";
        
        // 根据异常类型设置不同的错误信息
        if (exception instanceof BadCredentialsException) {
            errorMessage = "用户名或密码错误";
        } else if (exception instanceof DisabledException) {
            errorMessage = "账号已禁用";
        } else if (exception instanceof LockedException) {
            errorMessage = "账号已锁定";
        } else {
            errorMessage = exception.getMessage();
        }
        
        // 记录登录失败历史
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUsername(username);
        loginHistory.setIp(SecurityUtils.getClientIp(request));
        loginHistory.setLocation(""); // 可以通过IP获取地理位置
        loginHistory.setBrowser(request.getHeader("User-Agent"));
        loginHistory.setOs("");
        loginHistory.setStatus(1); // 失败
        loginHistory.setMsg(errorMessage);
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistoryMapper.insert(loginHistory);
        
        // 返回登录失败结果
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.error(401, errorMessage)));
        
        log.info("用户 {} 登录失败: {}", username, errorMessage);
    }
}

