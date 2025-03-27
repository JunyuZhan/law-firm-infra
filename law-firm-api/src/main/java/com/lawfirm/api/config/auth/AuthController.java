package com.lawfirm.api.config.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * API模块的授权控制器
 * 使用apiAuthController作为Bean名称以避免与law-firm-auth模块的authController冲突
 */
@Controller("apiAuthController")
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    @ResponseBody
    public String checkAuthStatus() {
        return "Auth system is running";
    }
} 