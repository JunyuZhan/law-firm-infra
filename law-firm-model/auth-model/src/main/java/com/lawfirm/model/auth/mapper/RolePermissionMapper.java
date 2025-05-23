package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.constant.AuthSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色-权限关联数据访问接口
 * 
 * @author JunyuZhan
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    
    /**
     * 通过角色ID查询角色权限关联
     * 
     * @param roleId 角色ID
     * @return 角色权限关联列表
     */
    @Select(AuthSqlConstants.RolePermission.SELECT_BY_ROLE_ID)
    List<RolePermission> selectByRoleId(Long roleId);
    
    /**
     * 通过权限ID查询角色权限关联
     * 
     * @param permissionId 权限ID
     * @return 角色权限关联列表
     */
    @Select(AuthSqlConstants.RolePermission.SELECT_BY_PERMISSION_ID)
    List<RolePermission> selectByPermissionId(Long permissionId);
    
    /**
     * 新增角色权限关联
     * 
     * @param rolePermission 角色权限关联实体
     * @return 影响行数
     */
    int insert(RolePermission rolePermission);
    
    /**
     * 批量新增角色权限关联
     * 
     * @param rolePermissions 角色权限关联实体列表
     * @return 影响行数
     */
    @Insert(AuthSqlConstants.RolePermission.INSERT_BATCH)
    int insertBatch(@Param("rolePermissions") List<RolePermission> rolePermissions);
    
    /**
     * 删除角色权限关联
     * 
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 影响行数
     */
    @Delete(AuthSqlConstants.RolePermission.DELETE)
    int delete(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    
    /**
     * 通过角色ID删除角色权限关联
     * 
     * @param roleId 角色ID
     * @return 影响行数
     */
    @Delete(AuthSqlConstants.RolePermission.DELETE_BY_ROLE_ID)
    int deleteByRoleId(Long roleId);
    
    /**
     * 通过权限ID删除角色权限关联
     * 
     * @param permissionId 权限ID
     * @return 影响行数
     */
    @Delete(AuthSqlConstants.RolePermission.DELETE_BY_PERMISSION_ID)
    int deleteByPermissionId(Long permissionId);
    
    /**
     * 查询是否存在角色权限关联
     * 
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 存在返回1，不存在返回0
     */
    @Select(AuthSqlConstants.RolePermission.EXISTS)
    int exists(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    
    /**
     * 获取角色的所有权限ID
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    @Select(AuthSqlConstants.RolePermission.SELECT_PERMISSION_IDS_BY_ROLE_ID)
    List<Long> selectPermissionIdsByRoleId(Long roleId);
} 