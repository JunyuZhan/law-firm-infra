package com.lawfirm.api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 直接登录控制器
 * 提供不经过Spring Security的简单登录API
 */
@RestController
@RequestMapping("/direct")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DirectLoginController {
    
    /**
     * 简单的JSON登录API
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo) {
        Map<String, Object> result = new HashMap<>();
        
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        if (username != null && password != null && 
            ("admin".equals(username) && "admin123".equals(password)) || 
            ("user".equals(username) && "password123".equals(password))) {
            
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", "test-token-" + System.currentTimeMillis());
            result.put("user", username);
            result.put("roles", username.equals("admin") ? new String[]{"ADMIN"} : new String[]{"USER"});
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        
        return result;
    }
    
    /**
     * GET方式登录，用于直接在浏览器测试
     */
    @GetMapping("/login-test")
    public Map<String, Object> loginTest(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        
        if (("admin".equals(username) && "admin123".equals(password)) || 
            ("user".equals(username) && "password123".equals(password))) {
            
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", "test-token-" + System.currentTimeMillis());
            result.put("user", username);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        
        return result;
    }
    
    /**
     * 状态检查API
     */
    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "running");
        result.put("time", System.currentTimeMillis());
        result.put("message", "系统正常运行");
        return result;
    }
} 