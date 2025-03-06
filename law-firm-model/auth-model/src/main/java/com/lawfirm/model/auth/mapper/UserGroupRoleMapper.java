package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.UserGroupRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组-角色关联Mapper接口
 */
@Mapper
public interface UserGroupRoleMapper extends BaseMapper<UserGroupRole> {
    
    /**
     * 根据用户组ID查询角色ID列表
     *
     * @param userGroupId 用户组ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserGroupId(@Param("userGroupId") Long userGroupId);
    
    /**
     * 根据用户组ID列表查询角色ID列表
     *
     * @param userGroupIds 用户组ID列表
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserGroupIds(@Param("userGroupIds") List<Long> userGroupIds);
    
    /**
     * 批量插入用户组角色关联
     *
     * @param userGroupRoles 用户组角色关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<UserGroupRole> userGroupRoles);
} 