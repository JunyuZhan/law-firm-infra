package com.lawfirm.api.controller;

import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.service.AuthService;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户信息控制器
 * 适配Vue-Vben-Admin
 */
@Tag(name = "用户信息")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public CommonResult<UserInfoVO> getUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserInfoVO userInfoVO = userService.getUserInfo(userId);
        return CommonResult.success(userInfoVO);
    }
    
    /**
     * 获取权限代码
     */
    @Operation(summary = "获取权限代码")
    @GetMapping("/getPermCode")
    public CommonResult<List<String>> getPermCode() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserInfoVO userInfo = userService.getUserInfo(userId);
        return CommonResult.success(userInfo.getPermissions());
    }
    
    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public CommonResult<Boolean> logout() {
        String username = SecurityUtils.getCurrentUsername();
        authService.logout(username);
        return CommonResult.success(true);
    }
} 