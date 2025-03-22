package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户-角色关联数据访问接口
 * 
 * @author JunyuZhan
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    
    /**
     * 通过用户ID查询用户角色关联
     * 
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    List<UserRole> selectByUserId(Long userId);
    
    /**
     * 通过角色ID查询用户角色关联
     * 
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    List<UserRole> selectByRoleId(Long roleId);
    
    /**
     * 新增用户角色关联
     * 
     * @param userRole 用户角色关联实体
     * @return 影响行数
     */
    int insert(UserRole userRole);
    
    /**
     * 批量新增用户角色关联
     * 
     * @param userRoles 用户角色关联实体列表
     * @return 影响行数
     */
    int insertBatch(@Param("userRoles") List<UserRole> userRoles);
    
    /**
     * 删除用户角色关联
     * 
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    int delete(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    /**
     * 通过用户ID删除用户角色关联
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(Long userId);
    
    /**
     * 通过角色ID删除用户角色关联
     * 
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(Long roleId);
    
    /**
     * 查询是否存在用户角色关联
     * 
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 存在返回1，不存在返回0
     */
    int exists(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    /**
     * 统计角色下的用户数量
     * 
     * @param roleId 角色ID
     * @return 用户数量
     */
    Long countUserByRoleId(Long roleId);
    
    /**
     * 查询角色下的所有用户ID
     * 
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> selectUserIdsByRoleId(Long roleId);
} 