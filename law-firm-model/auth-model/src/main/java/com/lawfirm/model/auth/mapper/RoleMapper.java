package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色数据访问接口
 * 
 * @author lawfirm
 */
public interface RoleMapper extends BaseMapper<Role> {
    
    /**
     * 根据角色编码查询角色
     * 
     * @param code 角色编码
     * @return 角色实体
     */
    Role selectByCode(String code);
    
    /**
     * 分页查询角色列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 角色名称（可选）
     * @return 角色列表
     */
    List<Role> selectPage(@Param("pageNum") Integer pageNum, 
                         @Param("pageSize") Integer pageSize,
                         @Param("name") String name);
    
    /**
     * 查询角色总数
     * 
     * @param name 角色名称（可选）
     * @return 角色总数
     */
    int selectCount(@Param("name") String name);
    
    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    List<Role> selectAll();
    
    /**
     * 查询角色所有权限ID
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByRoleId(Long roleId);
    
    /**
     * 查询用户的所有角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolesByUserId(Long userId);
} 