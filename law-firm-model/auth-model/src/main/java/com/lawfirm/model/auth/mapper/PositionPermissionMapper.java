package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.PositionPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职位权限关联数据访问接口
 */
@Mapper
public interface PositionPermissionMapper {
    
    /**
     * 根据ID查询职位权限关联
     *
     * @param id 职位权限关联ID
     * @return 职位权限关联信息
     */
    PositionPermission selectById(Long id);
    
    /**
     * 根据职位ID查询权限ID列表
     *
     * @param positionId 职位ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByPositionId(Long positionId);
    
    /**
     * 根据权限ID查询职位ID列表
     *
     * @param permissionId 权限ID
     * @return 职位ID列表
     */
    List<Long> selectPositionIdsByPermissionId(Long permissionId);
    
    /**
     * 添加职位权限关联
     *
     * @param positionPermission 职位权限关联信息
     * @return 影响行数
     */
    int insert(PositionPermission positionPermission);
    
    /**
     * 批量添加职位权限关联
     *
     * @param positionId     职位ID
     * @param permissionIds  权限ID列表
     * @return 影响行数
     */
    int batchInsert(@Param("positionId") Long positionId, @Param("permissionIds") List<Long> permissionIds);
    
    /**
     * 根据ID删除职位权限关联
     *
     * @param id 职位权限关联ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 根据职位ID删除职位权限关联
     *
     * @param positionId 职位ID
     * @return 影响行数
     */
    int deleteByPositionId(Long positionId);
    
    /**
     * 根据权限ID删除职位权限关联
     *
     * @param permissionId 权限ID
     * @return 影响行数
     */
    int deleteByPermissionId(Long permissionId);
    
    /**
     * 根据职位ID和权限ID删除职位权限关联
     *
     * @param positionId    职位ID
     * @param permissionId  权限ID
     * @return 影响行数
     */
    int deleteByPositionIdAndPermissionId(@Param("positionId") Long positionId, @Param("permissionId") Long permissionId);
} 