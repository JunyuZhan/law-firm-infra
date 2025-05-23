package com.lawfirm.model.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.constant.OrganizationSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 职位数据访问接口
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {
    
    /**
     * 根据部门ID查询职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    @Select(OrganizationSqlConstants.Position.FIND_BY_DEPARTMENT_ID)
    List<Position> findByDepartmentId(@Param("departmentId") Long departmentId);
    
    /**
     * 根据职位类型查询职位列表
     *
     * @param type 职位类型
     * @return 职位列表
     */
    @Select(OrganizationSqlConstants.Position.FIND_BY_TYPE)
    List<Position> findByType(@Param("type") Integer type);
    
    /**
     * 查询指定部门下的职位数量
     *
     * @param departmentId 部门ID
     * @return 职位数量
     */
    @Select(OrganizationSqlConstants.Position.COUNT_BY_DEPARTMENT_ID)
    int countByDepartmentId(@Param("departmentId") Long departmentId);
} 