package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.entity.SysUserRole;
import com.lawfirm.model.system.vo.SysUserRoleVO;

import java.util.List;

/**
 * 用户角色关联服务接口
 */
public interface SysUserRoleService extends BaseService<SysUserRole, SysUserRoleVO> {

    /**
     * 删除用户的所有角色关联
     */
    void deleteByUserId(Long userId);

    /**
     * 删除角色的所有用户关联
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量保存用户角色关联
     */
    boolean saveBatchUserRoles(List<SysUserRole> userRoles);

    /**
     * 批量保存用户角色关联
     */
    boolean saveBatch(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID查询角色关联列表
     */
    List<SysUserRoleVO> listByUserId(Long userId);

    /**
     * 根据角色ID查询用户关联列表
     */
    List<SysUserRoleVO> listByRoleId(Long roleId);
} 