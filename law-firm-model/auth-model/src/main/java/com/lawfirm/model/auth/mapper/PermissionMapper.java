package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限数据访问接口
 * 
 * @author lawfirm
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    
    /**
     * 根据权限编码查询权限
     * 
     * @param code 权限编码
     * @return 权限实体
     */
    Permission selectByCode(String code);
    
    /**
     * 查询所有权限
     * 
     * @return 权限列表
     */
    List<Permission> selectAll();
    
    /**
     * 根据类型查询权限
     * 
     * @param type 权限类型
     * @return 权限列表
     */
    List<Permission> selectByType(Integer type);
    
    /**
     * 查询菜单树
     * 
     * @return 菜单权限列表
     */
    List<Permission> selectMenuTree();
    
    /**
     * 根据父ID查询权限
     * 
     * @param parentId 父权限ID
     * @return 权限列表
     */
    List<Permission> selectByParentId(Long parentId);
    
    /**
     * 查询角色的所有权限
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByRoleId(Long roleId);
    
    /**
     * 查询用户的所有权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUserId(Long userId);

    /**
     * 查询员工权限编码列表
     */
    @Select("SELECT DISTINCT p.code FROM auth_permission p " +
            "INNER JOIN auth_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN auth_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectEmployeePermissionCodes(Long userId);

    /**
     * 查询用户菜单列表
     */
    @Select("SELECT DISTINCT p.* FROM auth_permission p " +
            "INNER JOIN auth_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN auth_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND p.type = 0 " +
            "ORDER BY p.sort")
    List<Permission> selectMenusByUserId(@Param("userId") Long userId);
} 