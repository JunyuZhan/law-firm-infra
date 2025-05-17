package com.lawfirm.model.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.organization.entity.department.Department;
import com.lawfirm.model.organization.constant.OrganizationSqlConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门数据访问接口
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 根据部门编码查询
     *
     * @param code 部门编码
     * @return 部门信息
     */
    @Select(OrganizationSqlConstants.Department.SELECT_BY_CODE)
    Department selectByCode(@Param("code") String code);

    /**
     * 查询子部门列表
     *
     * @param parentId 父部门ID
     * @return 子部门列表
     */
    @Select(OrganizationSqlConstants.Department.SELECT_BY_PARENT_ID)
    List<Department> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据部门类型查询
     *
     * @param type 部门类型
     * @return 部门列表
     */
    @Select(OrganizationSqlConstants.Department.SELECT_BY_TYPE)
    List<Department> selectByType(@Param("type") String type);

    /**
     * 根据律所ID查询部门列表
     *
     * @param firmId 律所ID
     * @return 部门列表
     */
    @Select(OrganizationSqlConstants.Department.SELECT_BY_FIRM_ID)
    List<Department> selectByFirmId(@Param("firmId") Long firmId);
} 