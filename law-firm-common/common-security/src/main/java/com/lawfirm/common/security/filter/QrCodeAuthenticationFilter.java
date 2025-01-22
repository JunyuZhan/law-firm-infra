package com.lawfirm.common.security.filter;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.service.QrCodeLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码登录认证过滤器
 */
@Slf4j
@Component
public class QrCodeAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private QrCodeLoginService qrCodeLoginService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws ServletException, IOException {
        // 只处理二维码登录请求
        if (!isQrCodeLoginRequest(request)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 获取二维码Token
            String qrCodeToken = getQrCodeToken(request);
            if (qrCodeToken != null) {
                // 验证二维码Token
                String username = qrCodeLoginService.verifyQrCodeToken(qrCodeToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("二维码登录成功，用户：{}", username);
                }
            }
        } catch (BusinessException e) {
            log.warn("二维码登录失败：{}", e.getMessage());
        } catch (Exception e) {
            log.error("二维码登录异常", e);
        }

        chain.doFilter(request, response);
    }

    /**
     * 判断是否为二维码登录请求
     */
    private boolean isQrCodeLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/qrcode/login");
    }

    /**
     * 获取二维码Token
     */
    private String getQrCodeToken(HttpServletRequest request) {
        String token = request.getHeader("QrCode-Token");
        if (token == null) {
            token = request.getParameter("qrcodeToken");
        }
        return token;
    }
} 