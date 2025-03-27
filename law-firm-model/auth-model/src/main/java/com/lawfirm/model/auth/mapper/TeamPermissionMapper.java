package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.TeamPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团队权限Mapper
 */
@Mapper
public interface TeamPermissionMapper extends BaseMapper<TeamPermission> {
    
    /**
     * 查询团队拥有的权限ID列表
     *
     * @param teamId 团队ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByTeamId(@Param("teamId") Long teamId);
    
    /**
     * 查询用户通过团队拥有的权限ID列表
     *
     * @param userId 用户ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByUserId(@Param("userId") Long userId);
} 