package com.lawfirm.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.model.system.dto.SysUserDTO;
import com.lawfirm.model.system.vo.SysUserVO;

import java.util.List;

/**
 * 系统用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建用户
     */
    @OperationLog(description = "创建用户", operationType = "INSERT")
    SysUserVO create(SysUserDTO user);

    /**
     * 更新用户
     */
    @OperationLog(description = "更新用户", operationType = "UPDATE")
    SysUserVO update(SysUserDTO user);

    /**
     * 删除用户
     */
    @OperationLog(description = "删除用户", operationType = "DELETE")
    void delete(Long userId);

    /**
     * 批量删除用户
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询用户
     */
    @OperationLog(description = "根据ID查询用户", operationType = "SELECT")
    SysUserVO findById(Long id);

    /**
     * 查询用户列表
     */
    List<SysUserVO> list(QueryWrapper<SysUser> wrapper);

    /**
     * 分页查询用户列表
     */
    PageResult<SysUserVO> page(Page<SysUser> page, QueryWrapper<SysUser> wrapper);

    /**
     * 分页查询用户列表
     */
    PageResult<SysUserVO> pageUsers(Integer pageNum, Integer pageSize, SysUserDTO query);

    /**
     * 重置密码
     */
    @OperationLog(description = "重置密码", operationType = "UPDATE")
    void resetPassword(Long userId, String password);

    /**
     * 修改密码
     */
    @OperationLog(description = "修改密码", operationType = "UPDATE")
    void changePassword(String oldPassword, String newPassword);

    /**
     * 更新头像
     */
    @OperationLog(description = "修改头像", operationType = "UPDATE")
    void updateAvatar(Long userId, String avatar);

    /**
     * 更新个人信息
     */
    @OperationLog(description = "修改个人信息", operationType = "UPDATE")
    SysUserVO updateProfile(SysUserDTO user);

    /**
     * 根据用户名查询用户
     */
    @OperationLog(description = "根据用户名查询用户", operationType = "SELECT")
    SysUserVO getByUsername(String username);

    /**
     * 根据部门ID查询用户列表
     */
    @OperationLog(description = "根据部门ID查询用户列表", operationType = "SELECT")
    List<SysUserVO> listByDeptId(Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    @OperationLog(description = "根据角色ID查询用户列表", operationType = "SELECT")
    List<SysUserVO> listByRoleId(Long roleId);

    /**
     * 分配用户角色
     */
    @OperationLog(description = "分配用户角色", operationType = "GRANT")
    void assignRoles(Long userId, List<Long> roleIds);
} 