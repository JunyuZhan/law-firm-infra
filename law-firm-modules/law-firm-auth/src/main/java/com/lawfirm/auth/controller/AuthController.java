package com.lawfirm.auth.controller;

import com.lawfirm.auth.annotation.RequirePermission;
import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.service.AuthService;
import com.lawfirm.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Tag(name = "认证管理", description = "提供用户认证相关接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 登录
     */
    @Operation(
        summary = "用户登录",
        description = "通过用户名密码进行登录，返回JWT令牌",
        responses = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "400", description = "用户名或密码错误"),
            @ApiResponse(responseCode = "403", description = "账户已禁用")
        }
    )
    @PostMapping("/login")
    public R<Map<String, Object>> login(
            @Parameter(description = "用户名", required = true, example = "admin")
            @NotBlank(message = "用户名不能为空") @RequestParam String username,
            @Parameter(description = "密码", required = true, example = "123456")
            @NotBlank(message = "密码不能为空") @RequestParam String password) {
        
        LoginUser loginUser = authService.login(username, password);
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", "Bearer " + loginUser.getToken());
        result.put("user", loginUser.getUser());
        
        return R.ok(result);
    }

    /**
     * 登出
     */
    @Operation(
        summary = "用户登出",
        description = "使当前JWT令牌失效",
        security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "登出成功"),
        @ApiResponse(responseCode = "401", description = "未认证"),
        @ApiResponse(responseCode = "403", description = "无权限")
    })
    @PostMapping("/logout")
    @RequirePermission("auth:logout")
    public R<Void> logout(
            @Parameter(description = "JWT令牌", required = true)
            @RequestHeader("Authorization") String token) {
        authService.logout(token);
        return R.ok();
    }

    /**
     * 刷新令牌
     */
    @Operation(
        summary = "刷新令牌",
        description = "使用当前JWT令牌获取新的令牌",
        security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "刷新成功"),
        @ApiResponse(responseCode = "401", description = "令牌已过期"),
        @ApiResponse(responseCode = "403", description = "无权限")
    })
    @PostMapping("/refresh")
    @RequirePermission("auth:refresh")
    public R<String> refreshToken(
            @Parameter(description = "JWT令牌", required = true)
            @RequestHeader("Authorization") String token) {
        String newToken = authService.refreshToken(token);
        return R.ok(newToken);
    }

    /**
     * 获取当前用户信息
     */
    @Operation(
        summary = "获取用户信息",
        description = "获取当前登录用户的详细信息",
        security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "获取成功"),
        @ApiResponse(responseCode = "401", description = "未认证"),
        @ApiResponse(responseCode = "403", description = "无权限")
    })
    @GetMapping("/info")
    @RequirePermission("auth:info")
    public R<LoginUser> getUserInfo(
            @Parameter(description = "JWT令牌", required = true)
            @RequestHeader("Authorization") String token) {
        LoginUser loginUser = authService.verifyToken(token);
        return R.ok(loginUser);
    }
} 