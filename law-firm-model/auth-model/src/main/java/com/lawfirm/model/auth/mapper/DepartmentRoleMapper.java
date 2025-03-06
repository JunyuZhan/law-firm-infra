package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.DepartmentRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门-角色关联Mapper接口
 */
@Mapper
public interface DepartmentRoleMapper extends BaseMapper<DepartmentRole> {
    
    /**
     * 根据部门ID查询角色ID列表
     *
     * @param departmentId 部门ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByDepartmentId(@Param("departmentId") Long departmentId);
    
    /**
     * 根据部门ID列表查询角色ID列表
     *
     * @param departmentIds 部门ID列表
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByDepartmentIds(@Param("departmentIds") List<Long> departmentIds);
    
    /**
     * 批量插入部门角色关联
     *
     * @param departmentRoles 部门角色关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<DepartmentRole> departmentRoles);
} 