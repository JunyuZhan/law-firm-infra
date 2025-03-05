package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限数据访问接口
 * 
 * @author lawfirm
 */
public interface PermissionMapper {
    
    /**
     * 根据ID查询权限
     * 
     * @param id 权限ID
     * @return 权限实体
     */
    Permission selectById(Long id);
    
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
     * 新增权限
     * 
     * @param permission 权限实体
     * @return 影响行数
     */
    int insert(Permission permission);
    
    /**
     * 更新权限
     * 
     * @param permission 权限实体
     * @return 影响行数
     */
    int update(Permission permission);
    
    /**
     * 删除权限
     * 
     * @param id 权限ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除权限
     * 
     * @param ids 权限ID数组
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
    
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
} 