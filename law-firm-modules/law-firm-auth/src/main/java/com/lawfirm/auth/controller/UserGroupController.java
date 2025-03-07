package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.usergroup.UserGroupCreateDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupQueryDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupUpdateDTO;
import com.lawfirm.model.auth.service.UserGroupService;
import com.lawfirm.model.auth.vo.UserGroupVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户组管理控制器
 */
@RestController
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    /**
     * 创建用户组
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:userGroup:add')")
    public CommonResult<Long> createUserGroup(@RequestBody @Valid UserGroupCreateDTO createDTO) {
        Long userGroupId = userGroupService.createUserGroup(createDTO);
        return CommonResult.success(userGroupId);
    }

    /**
     * 更新用户组
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> updateUserGroup(@PathVariable Long id, @RequestBody @Valid UserGroupUpdateDTO updateDTO) {
        userGroupService.updateUserGroup(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除用户组
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:userGroup:remove')")
    public CommonResult<Void> deleteUserGroup(@PathVariable Long id) {
        userGroupService.deleteUserGroup(id);
        return CommonResult.success();
    }

    /**
     * 批量删除用户组
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:userGroup:remove')")
    public CommonResult<Void> batchDeleteUserGroups(@RequestBody List<Long> ids) {
        userGroupService.deleteUserGroups(ids);
        return CommonResult.success();
    }

    /**
     * 获取用户组详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public CommonResult<UserGroupVO> getUserGroup(@PathVariable Long id) {
        UserGroupVO userGroupVO = userGroupService.getUserGroupById(id);
        return CommonResult.success(userGroupVO);
    }

    /**
     * 分页查询用户组
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public CommonResult<Page<UserGroupVO>> pageUserGroups(UserGroupQueryDTO queryDTO) {
        Page<UserGroupVO> page = userGroupService.pageUserGroups(queryDTO);
        return CommonResult.success(page);
    }

    /**
     * 获取所有用户组
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public CommonResult<List<UserGroupVO>> listAllUserGroups() {
        List<UserGroupVO> userGroups = userGroupService.listAllUserGroups();
        return CommonResult.success(userGroups);
    }

    /**
     * 获取用户组树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public CommonResult<List<UserGroupVO>> getUserGroupTree() {
        List<UserGroupVO> userGroupTree = userGroupService.getUserGroupTree();
        return CommonResult.success(userGroupTree);
    }

    /**
     * 更新用户组状态
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userGroupService.updateStatus(id, status);
        return CommonResult.success();
    }

    /**
     * 添加用户到用户组
     */
    @PutMapping("/{userGroupId}/users/{userId}")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> addUserToGroup(@PathVariable Long userGroupId, @PathVariable Long userId) {
        userGroupService.addUserToGroup(userGroupId, userId);
        return CommonResult.success();
    }

    /**
     * 批量添加用户到用户组
     */
    @PutMapping("/{userGroupId}/users/batch")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> addUsersToGroup(@PathVariable Long userGroupId, @RequestBody List<Long> userIds) {
        userGroupService.addUsersToGroup(userGroupId, userIds);
        return CommonResult.success();
    }

    /**
     * 从用户组移除用户
     */
    @DeleteMapping("/{userGroupId}/users/{userId}")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> removeUserFromGroup(@PathVariable Long userGroupId, @PathVariable Long userId) {
        userGroupService.removeUserFromGroup(userGroupId, userId);
        return CommonResult.success();
    }

    /**
     * 批量从用户组移除用户
     */
    @DeleteMapping("/{userGroupId}/users/batch")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public CommonResult<Void> removeUsersFromGroup(@PathVariable Long userGroupId, @RequestBody List<Long> userIds) {
        userGroupService.removeUsersFromGroup(userGroupId, userIds);
        return CommonResult.success();
    }

    /**
     * 获取用户所属用户组
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public CommonResult<List<UserGroupVO>> listUserGroupsByUserId(@PathVariable Long userId) {
        List<UserGroupVO> userGroups = userGroupService.listUserGroupsByUserId(userId);
        return CommonResult.success(userGroups);
    }
}
