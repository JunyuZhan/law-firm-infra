package com.lawfirm.auth.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.dto.user.UserUpdateDTO;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public CommonResult<UserVO> getUser(@PathVariable Long id) {
        return CommonResult.success(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public CommonResult<Long> createUser(@RequestBody UserCreateDTO createDTO) {
        Long userId = userService.createUser(createDTO);
        return CommonResult.success(userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public CommonResult<Void> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUser(id, updateDTO);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public CommonResult<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResult.success();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('user:update')")
    public CommonResult<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return CommonResult.success();
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('user:update')")
    public CommonResult<Void> updatePassword(@PathVariable Long id, 
                                           @RequestParam String oldPassword, 
                                           @RequestParam String newPassword) {
        userService.updatePassword(id, oldPassword, newPassword);
        return CommonResult.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/password/reset")
    @PreAuthorize("hasAuthority('user:update')")
    public CommonResult<String> resetPassword(@PathVariable Long id) {
        String newPassword = userService.resetPassword(id);
        return CommonResult.success(newPassword);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<UserInfoVO> getCurrentUserInfo() {
        Long userId = SecurityUtils.getUserId();
        UserInfoVO userInfo = userService.getUserInfo(userId);
        return CommonResult.success(userInfo);
    }
} 