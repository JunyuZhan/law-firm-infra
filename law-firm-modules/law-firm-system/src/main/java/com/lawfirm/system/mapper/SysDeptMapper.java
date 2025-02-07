package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    
    /**
     * 根据角色ID查询部门列表
     */
    @Select("SELECT dept_id FROM sys_role_dept WHERE role_id = #{roleId}")
    List<Long> selectDeptListByRoleId(Long roleId);
    
    /**
     * 查询部门是否存在用户
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE dept_id = #{deptId}")
    boolean checkDeptExistUser(Long deptId);
} 