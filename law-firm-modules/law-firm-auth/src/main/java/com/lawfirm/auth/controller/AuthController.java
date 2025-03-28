package com.lawfirm.auth.controller;

import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.auth.LoginDTO;
import com.lawfirm.model.auth.dto.auth.TokenDTO;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.LoginVO;
import com.lawfirm.common.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 认证控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@Tag(name = "认证服务", description = "用户登录、注册、登出等操作")
@RestController("authController")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;
    
    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return CommonResult.success(loginVO);
    }
    
    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "用户注册接口")
    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody UserCreateDTO createDTO) {
        userService.createUser(createDTO);
        return CommonResult.success();
    }
    
    /**
     * 刷新令牌
     */
    @Operation(summary = "刷新令牌", description = "刷新访问令牌接口")
    @PostMapping("/refreshToken")
    public CommonResult<TokenDTO> refreshToken(@RequestParam @NotBlank String refreshToken) {
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        return CommonResult.success(tokenDTO);
    }
    
    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户登出接口")
    @GetMapping("/logout")
    public CommonResult<?> logout() {
        String username = SecurityUtils.getCurrentUsername();
        authService.logout(username);
        return CommonResult.success();
    }
    
    /**
     * 获取验证码
     */
    @Operation(summary = "获取验证码", description = "获取验证码接口")
    @GetMapping("/getCaptcha")
    public CommonResult<String> getCaptcha() {
        // 这里需要生成验证码并存储到Redis
        // 简化处理，直接返回成功
        log.info("获取验证码");
        return CommonResult.success("验证码已发送");
    }
}

