package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.model.dto.SysUserDTO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface SysUserService extends BaseService<SysUser, SysUserDTO> {

    /**
     * 创建用户
     */
    @OperationLog(description = "创建用户", operationType = "INSERT")
    SysUserDTO createUser(SysUserDTO user);

    /**
     * 更新用户
     */
    @OperationLog(description = "更新用户", operationType = "UPDATE")
    SysUserDTO updateUser(SysUserDTO user);

    /**
     * 删除用户
     */
    @OperationLog(description = "删除用户", operationType = "DELETE")
    void deleteUser(Long userId);

    /**
     * 重置密码
     */
    @OperationLog(description = "重置密码", operationType = "UPDATE")
    void resetPassword(Long userId, String password);

    /**
     * 修改密码
     */
    @OperationLog(description = "修改密码", operationType = "UPDATE")
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 根据用户名获取用户
     */
    @OperationLog(description = "根据用户名查询用户", operationType = "SELECT")
    SysUserDTO getByUsername(String username);

    /**
     * 根据部门ID查询用户列表
     */
    @OperationLog(description = "根据部门ID查询用户列表", operationType = "SELECT")
    List<SysUserDTO> listByDeptId(Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    @OperationLog(description = "根据角色ID查询用户列表", operationType = "SELECT")
    List<SysUserDTO> listByRoleId(Long roleId);

    /**
     * 分配用户角色
     */
    @OperationLog(description = "分配用户角色", operationType = "GRANT")
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 修改个人信息
     */
    @OperationLog(description = "修改个人信息", operationType = "UPDATE")
    SysUserDTO updateProfile(SysUserDTO user);

    /**
     * 修改头像
     */
    @OperationLog(description = "修改头像", operationType = "UPDATE")
    void updateAvatar(Long userId, String avatar);

    /**
     * 更新用户角色
     */
    @OperationLog(description = "更新用户角色", operationType = "GRANT")
    void updateUserRoles(Long userId, List<Long> roleIds);
} 