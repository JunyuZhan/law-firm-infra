package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.service.AuthService;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public R<Map<String, Object>> getCaptcha() {
        // TODO: 验证码功能需要单独实现
        return R.ok();
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    @OperationLog(description = "用户登录", operationType = "LOGIN")
    public R<LoginUser> login(
            @NotBlank(message = "用户名不能为空") @RequestParam String username,
            @NotBlank(message = "密码不能为空") @RequestParam String password) {
        return R.ok(authService.login(username, password));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    @OperationLog(description = "用户登出", operationType = "LOGOUT")
    public R<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return R.ok();
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh-token")
    public R<String> refreshToken(@RequestHeader("Authorization") String token) {
        return R.ok(authService.refreshToken(token));
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    @OperationLog(description = "修改密码", operationType = "UPDATE")
    public R<Void> updatePassword(
            @NotBlank(message = "旧密码不能为空") @RequestParam String oldPassword,
            @NotBlank(message = "新密码不能为空") @RequestParam String newPassword) {
        // TODO: 修改密码功能需要单独实现
        return R.ok();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/reset-password")
    @OperationLog(description = "重置密码", operationType = "UPDATE")
    public R<Void> resetPassword(
            @NotBlank(message = "用户名不能为空") @RequestParam String username) {
        // TODO: 重置密码功能需要单独实现
        return R.ok();
    }
} 