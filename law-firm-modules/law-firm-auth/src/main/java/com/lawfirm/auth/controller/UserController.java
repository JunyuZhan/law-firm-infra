package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.dto.user.UserQueryDTO;
import com.lawfirm.model.auth.dto.user.UserUpdateDTO;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.auth.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.auth.constant.AuthConstants;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 用户控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@Tag(name = "用户管理", description = "用户管理接口")
@RestController("userController")
@RequestMapping(AuthConstants.Api.USER)
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * 创建用户
     */
    @Operation(summary = "创建用户", description = "创建新用户")
    @PostMapping
    @PreAuthorize("hasAuthority('" + SYS_USER_CREATE + "')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        Long userId = userService.createUser(createDTO);
        return CommonResult.success(userId);
    }
    
    /**
     * 更新用户
     */
    @Operation(summary = "更新用户", description = "更新用户信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_UPDATE + "')")
    public CommonResult<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUser(id, updateDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除用户
     */
    @Operation(summary = "删除用户", description = "删除指定用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_DELETE + "')")
    public CommonResult<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResult.success();
    }
    
    /**
     * 批量删除用户
     */
    @Operation(summary = "批量删除用户", description = "批量删除用户")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('" + SYS_USER_DELETE + "')")
    public CommonResult<?> batchDeleteUsers(@RequestBody List<Long> ids) {
        userService.deleteUsers(ids);
        return CommonResult.success();
    }
    
    /**
     * 获取用户详情
     */
    @Operation(summary = "获取用户详情", description = "获取指定用户详情")
    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_READ + "')")
    public CommonResult<UserVO> getUser(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return CommonResult.success(userVO);
    }
    
    /**
     * 分页查询用户
     */
    @Operation(summary = "分页查询用户", description = "分页查询用户列表")
    @GetMapping("/getUserPage")
    @PreAuthorize("hasAuthority('" + SYS_USER_READ + "')")
    public CommonResult<Page<UserVO>> getUserPage(UserQueryDTO queryDTO) {
        Page<UserVO> page = userService.pageUsers(queryDTO);
        return CommonResult.success(page);
    }
    
    /**
     * 修改密码
     */
    @Operation(summary = "修改密码", description = "修改用户密码")
    @PutMapping("/updatePassword/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_UPDATE + "') or #id == authentication.principal.userId")
    public CommonResult<?> updatePassword(@PathVariable Long id, 
                                         @RequestParam String oldPassword,
                                         @RequestParam String newPassword) {
        userService.updatePassword(id, oldPassword, newPassword);
        return CommonResult.success();
    }
    
    /**
     * 重置密码
     */
    @Operation(summary = "重置密码", description = "重置用户密码")
    @PutMapping("/resetPassword/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_UPDATE + "')")
    public CommonResult<String> resetPassword(@PathVariable Long id) {
        String newPassword = userService.resetPassword(id);
        return CommonResult.success(newPassword);
    }
    
    /**
     * 更新用户状态
     */
    @Operation(summary = "更新用户状态", description = "启用或禁用用户")
    @PutMapping("/updateStatus/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_UPDATE + "')")
    public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return CommonResult.success();
    }
    
    /**
     * 获取当前用户信息
     * 标准接口，适配vue-vben-admin格式
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户详细信息，适配vue-vben-admin")
    @GetMapping("/getUserInfo")
    public CommonResult<UserInfoVO> getUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserInfoVO userInfo = userService.getUserDetailInfo(userId);
        return CommonResult.success(userInfo);
    }
    
    /**
     * 分配用户角色
     */
    @Operation(summary = "分配用户角色", description = "为用户分配角色")
    @PutMapping("/assignRoles/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_UPDATE + "')")
    public CommonResult<?> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.assignRoles(id, roleIds);
        return CommonResult.success();
    }
    
    /**
     * 获取用户角色ID列表
     */
    @Operation(summary = "获取用户角色ID列表", description = "获取用户已分配的角色ID列表")
    @GetMapping("/getUserRoles/{id}")
    @PreAuthorize("hasAuthority('" + SYS_USER_READ + "')")
    public CommonResult<List<Long>> getUserRoles(@PathVariable Long id) {
        List<Long> roleIds = userService.getUserRoleIds(id);
        return CommonResult.success(roleIds);
    }
} 