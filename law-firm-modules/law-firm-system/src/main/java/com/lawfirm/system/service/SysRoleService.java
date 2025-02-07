package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.system.entity.SysRole;
import com.lawfirm.model.system.vo.SysRoleVO;

import java.util.List;

/**
 * 角色服务接口
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleVO> {

    /**
     * 创建角色
     */
    @OperationLog(description = "创建角色", operationType = "CREATE")
    SysRoleVO createRole(SysRoleVO role);

    /**
     * 更新角色
     */
    @OperationLog(description = "更新角色", operationType = "UPDATE")
    SysRoleVO updateRole(SysRoleVO role);

    /**
     * 删除角色
     */
    @OperationLog(description = "删除角色", operationType = "DELETE")
    void deleteRole(Long id);

    /**
     * 根据角色编码查询角色
     */
    SysRoleVO getByCode(String code);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRoleVO> listByUserId(Long userId);

    /**
     * 查询默认角色列表
     */
    List<SysRoleVO> listDefaultRoles();

    /**
     * 分配角色菜单
     */
    @OperationLog(description = "分配角色菜单", operationType = "GRANT")
    void assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 分配角色数据权限
     */
    @OperationLog(description = "分配角色数据权限", operationType = "GRANT")
    void assignDataScope(Long roleId, Integer dataScope);

    /**
     * 根据查询条件获取角色列表
     */
    List<SysRoleVO> list(SysRoleVO query);
} 