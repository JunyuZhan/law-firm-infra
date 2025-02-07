package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.admin.client.auth.AuthClient;
import com.lawfirm.admin.model.request.auth.*;
import com.lawfirm.admin.model.response.auth.LoginResponse;
import com.lawfirm.admin.model.response.auth.CaptchaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResponse> getCaptcha() {
        return authClient.getCaptcha();
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    @OperationLog(description = "用户登录", operationType = "LOGIN")
    public Result<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        return authClient.login(request);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    @OperationLog(description = "用户登出", operationType = "LOGOUT")
    public Result<Void> logout() {
        return authClient.logout();
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh-token")
    public Result<LoginResponse> refreshToken(@RequestBody @Validated RefreshTokenRequest request) {
        return authClient.refreshToken(request);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    @OperationLog(description = "修改密码", operationType = "UPDATE")
    public Result<Void> updatePassword(@RequestBody @Validated UpdatePasswordRequest request) {
        return authClient.updatePassword(request);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/reset-password")
    @OperationLog(description = "重置密码", operationType = "UPDATE")
    public Result<Void> resetPassword(@RequestBody @Validated ResetPasswordRequest request) {
        return authClient.resetPassword(request);
    }
} 