package com.lawfirm.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

/**
 * 安全配置测试控制器
 */
@RestController("TestSecurityController")
@RequestMapping("/test")
public class TestSecurityController {

    /**
     * 测试安全配置
     * 返回当前安全上下文信息
     */
    @GetMapping("/security-info")
    public Map<String, Object> getSecurityInfo() {
        Map<String, Object> info = new HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        info.put("securityEnabled", auth != null);
        info.put("authenticated", auth != null && auth.isAuthenticated());
        
        if (auth != null) {
            info.put("principal", auth.getPrincipal().toString());
            info.put("authorities", auth.getAuthorities().toString());
            info.put("details", auth.getDetails() != null ? auth.getDetails().toString() : null);
        }
        
        info.put("activeProfiles", System.getProperty("spring.profiles.active"));
        info.put("simplifiedSecurity", System.getProperty("dev.auth.simplified-security"));
        
        return info;
    }
    
    /**
     * 公开测试接口
     * 此接口允许匿名访问，验证权限配置
     */
    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "这是一个公开接口，无需认证");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return response;
    }
} 