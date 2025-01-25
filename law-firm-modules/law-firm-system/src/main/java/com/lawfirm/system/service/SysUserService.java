package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysUser;

import java.util.List;

/**
 * 系统用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建用户
     */
    @Log(module = "用户管理", businessType = BusinessType.INSERT, description = "创建用户")
    void createUser(SysUser user);

    /**
     * 更新用户
     */
    @Log(module = "用户管理", businessType = BusinessType.UPDATE, description = "更新用户")
    void updateUser(SysUser user);

    /**
     * 删除用户
     */
    @Log(module = "用户管理", businessType = BusinessType.DELETE, description = "删除用户")
    void deleteUser(Long id);

    /**
     * 重置密码
     */
    @Log(module = "用户管理", businessType = BusinessType.UPDATE, description = "重置用户密码")
    void resetPassword(Long id, String password);

    /**
     * 修改密码
     */
    @Log(module = "用户管理", businessType = BusinessType.UPDATE, description = "修改密码")
    void changePassword(Long id, String oldPassword, String newPassword);

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);

    /**
     * 根据部门ID查询用户列表
     */
    List<SysUser> listByDeptId(Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    List<SysUser> listByRoleId(Long roleId);

    /**
     * 分配角色
     */
    @Log(module = "用户管理", businessType = BusinessType.GRANT, description = "分配用户角色")
    void assignRoles(Long userId, List<Long> roleIds);
} 