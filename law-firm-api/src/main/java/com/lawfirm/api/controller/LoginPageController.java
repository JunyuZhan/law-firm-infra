package com.lawfirm.api.controller;

import com.lawfirm.api.adaptor.auth.AuthAdaptor;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录页面控制器
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginPageController {
    
    private final AuthAdaptor authAdaptor;

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 返回login视图
    }
    
    /**
     * 登录处理
     */
    @PostMapping("/login-process")
    @ResponseBody
    public CommonResult<LoginVO> loginProcess(@RequestBody LoginDTO loginDTO) {
        try {
            return CommonResult.success(authAdaptor.login(loginDTO));
        } catch (Exception e) {
            return CommonResult.error("登录失败：" + e.getMessage());
        }
    }
    
    /**
     * 登录API - 测试用
     */
    @PostMapping("/simple-login")
    @ResponseBody
    public Map<String, Object> simpleLogin(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        
        if ("admin".equals(username) && "admin123".equals(password)) {
            result.put("status", "success");
            result.put("message", "登录成功");
            result.put("token", "test-token-" + System.currentTimeMillis());
        } else {
            result.put("status", "error");
            result.put("message", "用户名或密码错误");
        }
        
        return result;
    }
} 